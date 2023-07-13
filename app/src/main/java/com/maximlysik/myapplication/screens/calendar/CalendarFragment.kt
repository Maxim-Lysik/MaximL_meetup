package com.maximlysik.myapplication.screens.calendar

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.maximlysik.myapplication.R
import com.maximlysik.myapplication.databinding.FragmentCalendarBinding
import com.maximlysik.myapplication.screens.OurViewModelFactory
import com.maximlysik.myapplication.screens.common.fragments.BaseFragment
import javax.inject.Inject


class CalendarFragment : BaseFragment() {


    @Inject
    lateinit var ourViewModelFactory: OurViewModelFactory
    private lateinit var calendarViewModel: CalendarViewModel

    private var _binding: FragmentCalendarBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        injector.inject(this)

        calendarViewModel = ViewModelProvider(this, ourViewModelFactory).get(CalendarViewModel::class.java)



        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root


   binding.floatingActionButton2.setOnClickListener {

       Log.d(ContentValues.TAG, "CALENDAR_FRAGMENT");

   }

        binding.addEventButton.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_navigation_calendar_to_newEventFragment)
        }


        val textView: TextView = binding.textHome


        val activity: androidx.appcompat.app.AppCompatActivity


        return root

    }


}