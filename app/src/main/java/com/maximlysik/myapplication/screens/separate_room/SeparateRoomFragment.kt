package com.maximlysik.myapplication.screens.separate_room

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.maximlysik.domain.entities.Room
import com.maximlysik.domain.repositories.ScreensNavigator
import com.maximlysik.myapplication.R
import com.maximlysik.myapplication.databinding.FragmentSeparateRoomBinding
import com.maximlysik.myapplication.screens.OurViewModelFactory
import com.maximlysik.myapplication.screens.common.fragments.BaseFragment
import com.maximlysik.myapplication.screens.rooms.RoomsViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class SeparateRoomFragment : BaseFragment() {

    @Inject
    lateinit var ourViewModelFactory: OurViewModelFactory
    private lateinit var separateRoomViewModel: SeparateRoomViewModel
    private var _binding: FragmentSeparateRoomBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var screensNavigator: ScreensNavigator


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injector.inject(this)
        separateRoomViewModel =
            ViewModelProvider(this, ourViewModelFactory).get(SeparateRoomViewModel::class.java)

        _binding = FragmentSeparateRoomBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val bundle = this.arguments
        if (bundle != null) {
            val receivedRoom = bundle.getSerializable("our_room") as Room
            Log.d(ContentValues.TAG, "Received room $receivedRoom");
            separateRoomViewModel.setRoom(receivedRoom)

        }

        separateRoomViewModel.separate_room.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                setupUI(it)
            })


        binding.deleteRoomButton.setOnClickListener {
            lifecycleScope.launch {
                val bundle = this@SeparateRoomFragment.arguments
                val receivedRoom = bundle!!.getSerializable("our_room") as Room
                separateRoomViewModel.deleteRoom(receivedRoom.roomId.toString())
            }
        }



        binding.updateButton.setOnClickListener {
            lifecycleScope.launch {
                 separateRoomViewModel.updateRoom(getDataTpUpdateRoom())
            }
        }




        return root


    }

    fun setupUI(room: Room) {

        binding.roomNameField.setText(room.name)
        binding.roomNumberField.setText(room.number)
        binding.roomCapacityField.setText(room.capacity)
        binding.roomLevelField.setText(room.floor)

        if (room.display_presence == true) {
            binding.displayStatus.text = getString(R.string.positive_answer)

        } else {
            binding.displayStatus.text = getString(R.string.negative_answer)
        }

        if (room.speakers_presence == true) {
            binding.speakersStatus.text = getString(R.string.positive_answer)
        } else {
            binding.speakersStatus.text = getString(R.string.negative_answer)
        }
    }


    fun getDataTpUpdateRoom(): Room {
        val roomToUpdate: Room

        val roomName = binding.roomNameField.text.toString()
        val roomNumber = binding.roomNumberField.text.toString()
        val roomCapacity = binding.roomCapacityField.text.toString()
        val roomFloor = binding.roomLevelField.text.toString()
        var displayAvailability: Boolean
        var speakersAvailability: Boolean

      if (binding.displayStatus.text.equals("Yes")) {
            displayAvailability = true
        } else {
            displayAvailability = false
        }

        if (binding.speakersStatus.text.equals("Yes")) {
            speakersAvailability = true
        } else {
            speakersAvailability = false
        }


        val bundle = this@SeparateRoomFragment.arguments
        val initialRoom = bundle!!.getSerializable("our_room") as Room


        roomToUpdate = Room(roomName,initialRoom.roomId,roomNumber,roomFloor, roomCapacity, displayAvailability, speakersAvailability )
        return roomToUpdate

    }

}