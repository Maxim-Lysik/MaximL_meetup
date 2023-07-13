package com.maximlysik.myapplication.screens.rooms

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.maximlysik.domain.entities.Room
import com.maximlysik.domain.repositories.ScreensNavigator
import com.maximlysik.myapplication.R
import com.maximlysik.myapplication.databinding.FragmentRoomsBinding
import com.maximlysik.myapplication.screens.OurViewModelFactory
import com.maximlysik.myapplication.screens.common.fragments.BaseFragment
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


class RoomsFragment : BaseFragment(){


    @Inject
    lateinit var ourViewModelFactory: OurViewModelFactory
    private lateinit var roomsViewModel: RoomsViewModel
    private var _binding: FragmentRoomsBinding? = null
    lateinit var roomsAdapter: RoomsAdapter
    private val binding get() = _binding!!

    @Inject
    lateinit var screensNavigator: ScreensNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injector.inject(this)
         roomsViewModel = ViewModelProvider(this, ourViewModelFactory).get(RoomsViewModel::class.java)

        roomsViewModel.rooms.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            connectAdapter(it)
        })

        _binding = FragmentRoomsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.doneButton.setOnClickListener {
        }


        binding.addRoomsButton.setOnClickListener {
            lifecycleScope.launch {
                val activity = requireActivity()
                activity.findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_navigation_rooms_to_addRoomFragment)


            }
        }


        return root
    }

    // SETTING UP ADAPTER
    private fun connectAdapter(roomsList: List<Room>) {
        roomsAdapter = RoomsAdapter(this.requireActivity(), roomsList)
        roomsAdapter.bindData(roomsList)

        binding.roomsRecycler.apply {
            adapter = roomsAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
    }
}