package com.maximlysik.myapplication.screens.events

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.maximlysik.domain.entities.Event
import com.maximlysik.domain.entities.Room
import com.maximlysik.myapplication.databinding.FragmentUserEventsBinding
import com.maximlysik.myapplication.screens.OurViewModelFactory
import com.maximlysik.myapplication.screens.common.fragments.BaseFragment
import com.maximlysik.myapplication.screens.rooms.RoomsAdapter
import com.maximlysik.myapplication.screens.rooms.RoomsViewModel
import javax.inject.Inject

class UserEventsFragment : BaseFragment() {

    @Inject
    lateinit var ourViewModelFactory: OurViewModelFactory

    private lateinit var userEventsViewModel: UserEventsViewModel
    lateinit var eventsAdapter: EventsAdapter
    private var _binding: FragmentUserEventsBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        injector.inject(this)

        userEventsViewModel = ViewModelProvider(this, ourViewModelFactory).get(UserEventsViewModel::class.java)


        val sharedPrefeshared = requireActivity().applicationContext.getSharedPreferences("USER_NAME",   Context.MODE_PRIVATE)


        val userName = sharedPrefeshared.getString("Current_user", "nothing") //Optional parameters

        userEventsViewModel.getEvents(userName.toString())

        userEventsViewModel.events.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            connectAdapter(it)
        })

        _binding = FragmentUserEventsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.eventsDoneButton.setOnClickListener {
        }


        return root
    }



    // SETTING UP ADAPTER
    private fun connectAdapter(eventsList: List<Event>) {
        eventsAdapter = EventsAdapter(this.requireActivity(), eventsList)
        eventsAdapter.bindData(eventsList)

        binding.currentUserEvents.apply {
            adapter = eventsAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            eventsAdapter.notifyDataSetChanged()
        }
    }
}