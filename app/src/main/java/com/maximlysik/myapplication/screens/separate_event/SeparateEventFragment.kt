package com.maximlysik.myapplication.screens.separate_event

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.maximlysik.domain.entities.Event
import com.maximlysik.domain.entities.Room
import com.maximlysik.myapplication.R
import com.maximlysik.myapplication.databinding.FragmentRoomsBinding
import com.maximlysik.myapplication.databinding.FragmentSeparateEventBinding
import com.maximlysik.myapplication.databinding.FragmentUserEventsBinding
import com.maximlysik.myapplication.screens.OurViewModelFactory
import com.maximlysik.myapplication.screens.common.fragments.BaseFragment
import com.maximlysik.myapplication.screens.events.EventsAdapter
import com.maximlysik.myapplication.screens.events.UserEventsViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone
import java.util.UUID
import javax.inject.Inject


class SeparateEventFragment : BaseFragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    @Inject
    lateinit var ourViewModelFactory: OurViewModelFactory

    private lateinit var separateEventViewModel: SeparateEventViewModel
    private var _binding: FragmentSeparateEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injector.inject(this)

        separateEventViewModel = ViewModelProvider(this, ourViewModelFactory).get(SeparateEventViewModel::class.java)
        _binding = FragmentSeparateEventBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val bundle = this.arguments
        if (bundle != null) {
            val receivedEvent = bundle.getSerializable("our_event") as Event
            Log.d(ContentValues.TAG, "Received event $receivedEvent");
            separateEventViewModel.setEvent(receivedEvent)
        }


        separateEventViewModel.event.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
            binding.separateEventName.setText(it.name.toString())
                binding.separateEventStartTime.text = it.starting_time.toString()
                binding.separateEventEndTime.text = it.finish_time.toString()
                binding.separateEventChosenUsers.text = it.participants.toString()
                binding.separateEventChosenRoom.text = it.room
            })

        separateEventViewModel.startTime.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                binding.separateEventStartTime.text = it.toString()
            })

        separateEventViewModel.endTime.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                binding.separateEventEndTime.text = it.toString()
            })


        separateEventViewModel.selectedUsers.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                binding.separateEventChosenUsers.text = it.joinToString(separator = ", ", limit = 30)
            })


        separateEventViewModel.chosenRoom.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                binding.separateEventChosenRoom.text = it.toString()
            })

        binding.separateEventButtonStartTime.setOnClickListener {
            startTimeChosen = false
            getDateTimeCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        binding.separateEventButtonEndTime.setOnClickListener {
            startTimeChosen = true
            getDateTimeCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }


        binding.separateEventButtonChooseUsers.setOnClickListener {

            val arrayListOfUsersNames: ArrayList<String> = arrayListOf<String>()

            separateEventViewModel.users.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    for (i in it) {
                        arrayListOfUsersNames.add(i.login.toString())
                    }
                })

            val arrayOfUserNames: Array<String> = arrayListOfUsersNames.toTypedArray()
            val checkedUsers = BooleanArray(arrayOfUserNames.size)
            val selectedItems = mutableListOf(*arrayOfUserNames)

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Choose Items")
            // now this is the function which sets the alert dialog for multiple item selection ready
            builder.setMultiChoiceItems(
                arrayOfUserNames,
                checkedUsers
            ) { dialog, which, isChecked ->
                checkedUsers[which] = isChecked
                val currentItem = selectedItems[which]
            }

            // alert dialog shouldn't be cancellable
            builder.setCancelable(false)

            // handle the positive button of the dialog
            builder.setPositiveButton("Done") { dialog, which ->
                var returnedItems: ArrayList<String> = arrayListOf<String>()
                for (i in checkedUsers.indices) {
                    if (checkedUsers[i]) {
                        returnedItems.add(selectedItems[i])
                    }

                }
                val namesOfUsers = returnedItems.joinToString(separator = ", ", limit = 30)
                binding.separateEventChosenUsers.text = namesOfUsers
                separateEventViewModel.setSelectedUsers(returnedItems.toList())
            }
            builder.setNegativeButton("CANCEL") { dialog, which -> }
            builder.setNeutralButton("CLEAR ALL") { dialog: DialogInterface?, which: Int ->
            }
            builder.create()
            val alertDialog = builder.create()
            alertDialog.show()
        }


        binding.updateReturnButton.setOnClickListener {
            showBothTimes()
        }

        binding.separateEventButtonChooseRoom.setOnClickListener {

            val arrayListOfRoomsNames: ArrayList<String> = arrayListOf<String>()

            separateEventViewModel.rooms.observe(
                viewLifecycleOwner,
                androidx.lifecycle.Observer {
                    for (i in it) {
                        arrayListOfRoomsNames.add(i.name.toString())
                    }
                })
            val arrayOfUserNames: Array<String> = arrayListOfRoomsNames.toTypedArray()

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Choose Items")
            // now this is the function which sets the alert dialog for multiple item selection ready
            var checkedIndex = -1
            builder.setSingleChoiceItems(arrayOfUserNames, -1, { dialog, index ->
                checkedIndex = index
                separateEventViewModel.setChosenRoom(arrayOfUserNames[index].toString())
            })
            builder.setCancelable(false)
            builder.setPositiveButton("Done", ({ dialog, id ->
            }))
            builder.setNegativeButton("CANCEL") { dialog, which -> }
            builder.setNeutralButton("CLEAR ALL") { dialog: DialogInterface?, which: Int ->
            }
            builder.create()
            val alertDialog = builder.create()
            alertDialog.show()
        }


        binding.buttonDeleteEvent.setOnClickListener {
           lifecycleScope.launch {
               if (bundle != null) {
                   val receivedEvent = bundle.getSerializable("our_event") as Event
                   Log.d(ContentValues.TAG, "Received event $receivedEvent");
                   separateEventViewModel.deleteEvent(receivedEvent.eventId.toString())
                   Toast.makeText(requireContext(), "Event deleted", Toast.LENGTH_SHORT).show()
               }
            }
        }
        return root
    }



    @SuppressLint("SuspiciousIndentation")
    fun showBothTimes() {

        // CHECKING IF NEEDED VALUES & FIELDS ARE NOT EMPTY

        val eventName: String = binding.separateEventName.text.toString()
        val startingTime: String = binding.separateEventStartTime.text.toString()
        val endingTime: String = binding.separateEventEndTime.text.toString()
        val participants: String = binding.separateEventChosenUsers.text.toString()
        val chosenRoom: String = binding.separateEventChosenRoom.text.toString()


        if (eventName.length > 0 && startingTime.length > 0 && endingTime.length > 0 && participants.length > 0 && chosenRoom.length > 0
        ) {
            val utc = TimeZone.getTimeZone("UTC")
            val sourceFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
            sourceFormat.timeZone = utc
            val startingTimeFormatted = sourceFormat.parse(startingTime)
            val endingTimeFormatted = sourceFormat.parse(endingTime)

            // Проверяем чтобы дата начала не была больше даты завершения


                if (endingTimeFormatted > startingTimeFormatted) {

                // Проверяем чтобы наш период был от 30 минут до 24 часов

                val differeBetweenDatesInMinutes =
                    ((endingTimeFormatted.time - startingTimeFormatted.time) / 1000) / 60
                val startingInterval = 30
                val closingInterval = 1440


                if (differeBetweenDatesInMinutes >= 30 && differeBetweenDatesInMinutes <= 1440) {

                   val eventID = UUID.randomUUID().toString()  // Генерируем ID для нашего события

                    val bundle = this.arguments
                    if (bundle != null) {
                        val receivedEvent = bundle.getSerializable("our_event") as Event
                        separateEventViewModel.setEvent(receivedEvent)

                        lifecycleScope.launch {
                            separateEventViewModel.deleteEvent(receivedEvent.eventId.toString())
                        }

                        var our_event =  Event(eventID,
                            eventName,
                            startingTime,
                            endingTime,
                            participants,
                            chosenRoom
                        )

                        val booleana: Boolean =  checkEventDuplicates(our_event)

                        loadEventAfterChecking(our_event, booleana)

                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please choose period from 30m to 24h",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {

                Toast.makeText(
                    requireContext(),
                    "Starting time should not be longer than ending time!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        } else {
            Toast.makeText(requireContext(), "All field should be filled!", Toast.LENGTH_SHORT)
                .show()
        }
    }


    fun checkEventDuplicates(event: Event): Boolean {
        var ourBoolean = false

        separateEventViewModel.events.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                var counter = 0

                val utc = TimeZone.getTimeZone("UTC")

                val sourceFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
                sourceFormat.timeZone = utc

                var flagIfCheckingIsOk = false

                if(it.size>0){

                    for (i in it) {
                        // i.starting_time
                        val start_time_received_data = sourceFormat.parse(i.starting_time)
                        val end_time_received_data = sourceFormat.parse(i.finish_time)
                        val start_time_input_data = sourceFormat.parse(event.starting_time)
                        val end_time_input_data = sourceFormat.parse(event.finish_time)
                        // CHECKING IF OUR EVENT IS WITHIN INTERVAL

                        if ((counter ==0 && start_time_input_data > start_time_received_data
                                    && start_time_input_data < end_time_received_data
                                    && end_time_input_data > start_time_received_data
                                    && end_time_input_data > end_time_received_data) ||
                            (counter ==0 && start_time_input_data < start_time_received_data
                                    && start_time_input_data < end_time_received_data
                                    && end_time_input_data > start_time_received_data
                                    && end_time_input_data < end_time_received_data)
                        ) {
                            flagIfCheckingIsOk = true
                            ourBoolean = true
                        }
                    }
                }
            })
        return ourBoolean

    }



    fun loadEventAfterChecking(event: Event, boolean: Boolean){
        if (!boolean){
            lifecycleScope.launch {
                separateEventViewModel.addEvent(
                    event
                ) }
            Toast.makeText(requireContext(), "Event created", Toast.LENGTH_SHORT).show()
        } else{
            val bundle = this.arguments
            if (bundle != null) {
                val receivedEvent = bundle.getSerializable("our_event") as Event
                separateEventViewModel.setEvent(receivedEvent)
            }
            Toast.makeText(requireContext(), "Duplicate", Toast.LENGTH_SHORT).show()
        }
    }





    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        if (startTimeChosen == true) {
            savedDayFinish = p3
            savedMonthFinish = p2
            savedYearFinish = p1
        } else {
            savedDayStart = p3
            savedMonthStart = p2
            savedYearStart = p1

        }
        getDateTimeCalendar()
        TimePickerDialog(requireContext(), this, hour, minute, true).show()
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        if (startTimeChosen == true) {
            savedHourFinish = p1
            savedMinuteFinish = p2
            binding.separateEventEndTime.text =
                "$savedYearFinish-$savedMonthFinish-$savedDayFinish $savedHourFinish:$savedMinuteFinish"

            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val text =
                "$savedYearFinish-$savedMonthFinish-$savedDayFinish $savedHourFinish:$savedMinuteFinish:00"
            val finish_time = formatter.parse(text)
            separateEventViewModel.setEndTime(finish_time)

        } else {
            savedHourStart = p1
            savedMinuteStart = p2
            binding.separateEventStartTime.text =
                "$savedYearStart-$savedMonthStart-$savedDayStart $savedHourStart:$savedMinuteStart"

            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val text =
                "$savedYearStart-$savedMonthStart-$savedDayStart $savedHourStart:$savedMinuteStart:00"
            val starting_time = formatter.parse(text)
            separateEventViewModel.setStartTime(starting_time)
        }
    }

}