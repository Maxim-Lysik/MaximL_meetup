package com.maximlysik.myapplication.screens.add_room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.maximlysik.domain.entities.Room
import com.maximlysik.myapplication.databinding.FragmentAddRoomBinding
import com.maximlysik.myapplication.screens.OurViewModelFactory
import com.maximlysik.myapplication.screens.common.fragments.BaseFragment
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


class AddRoomFragment : BaseFragment() {

    @Inject
    lateinit var ourViewModelFactory: OurViewModelFactory
    private lateinit var addRoomViewModel: AddRoomViewModel
    private var _binding: FragmentAddRoomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injector.inject(this)
        addRoomViewModel =
            ViewModelProvider(this, ourViewModelFactory).get(AddRoomViewModel::class.java)

        _binding = FragmentAddRoomBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.addRoomButton.setOnClickListener { lifecycleScope.launch { addRoom() } }
        return root
    }


    fun addRoom() {

        val room_number: String = binding.roomNumberField.text.toString()
        val room_name: String = binding.roomNameField.text.toString()
        val room_capacity: String = binding.roomCapacity.text.toString()
        val room_level: String = binding.roomLevel.text.toString()
        val roomID = UUID.randomUUID().toString()  // Генерируем ID для нашей аудитории
        var speakers_availability: Boolean = false
        var display_availability: Boolean = false

        // Проверяем значения чекбоксов

        if (binding.speakersCheckbox.isChecked) {
            speakers_availability = true
        }
        if (binding.displayCheckbox.isChecked) {
            display_availability = true
        }

        // Проверяем заполненность полей

        if (room_number.length > 0 && room_name.length > 0 && room_capacity.length > 0 && room_level.length > 0
        ) {
            lifecycleScope.launch {
                addRoomViewModel.addRoom(
                    Room(room_name, roomID, room_number, room_level, room_capacity, display_availability, speakers_availability
                    )
                )
            }
            Toast.makeText(requireContext(), "Room was added", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "All fields should be filled!", Toast.LENGTH_SHORT)
                .show()
        }

    }
}