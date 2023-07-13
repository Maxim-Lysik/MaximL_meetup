package com.maximlysik.myapplication.screens.separate_event

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximlysik.domain.entities.Event
import com.maximlysik.domain.entities.Room
import com.maximlysik.domain.entities.User
import com.maximlysik.domain.repositories.FirebaseRepository
import com.maximlysik.domain.usecases.AddEventUseCase
import com.maximlysik.domain.usecases.DeleteEventUseCase
import com.maximlysik.domain.usecases.GetEventsUseCase
import com.maximlysik.domain.usecases.GetRoomsUseCase
import com.maximlysik.domain.usecases.GetUsersUseCase
import com.maximlysik.domain.usecases.UpdateEventUseCase
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class SeparateEventViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val firebaseRepository: FirebaseRepository,
    private val getRoomsUseCase: GetRoomsUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val updateEventUseCase: UpdateEventUseCase,
    private val addEventUseCase: AddEventUseCase

    ) : ViewModel() {

    private var _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    fun setEvent(receivedEvent:Event){
        _event.value = receivedEvent
    }

    private var _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private var _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> = _rooms

    private var _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events



    // VARIABLE FOR CHOSEN USERS VIA ALERT-DIALOG
    var _selectedUsers = MutableLiveData<List<String>>()
    var selectedUsers: LiveData<List<String>> = _selectedUsers
    fun setSelectedUsers(users:List<String>){
        _selectedUsers.value = users
    }

    // VARIABLE FOR CHOSEN START TIME
    var _startTime = MutableLiveData<Date>()
    var startTime: LiveData<Date> = _startTime
    fun setStartTime(time: Date){
        _startTime.value = time
    }

    // VARIABLE FOR CHOSEN START TIME
    var _endTime = MutableLiveData<Date>()
    var endTime: LiveData<Date> = _endTime
    fun setEndTime(time: Date){
        _endTime.value = time
    }

    // VARIABLE FOR CHOSEN ROOM
    var _chosenRoom = MutableLiveData<String>()
    var chosenRoom: LiveData<String> = _chosenRoom
    fun setChosenRoom(room:String){
        _chosenRoom.value = room
    }


    fun getEvents() = viewModelScope.launch {
        getEventsUseCase().collect { response ->
            _events.value = response
            for (i in response){
            }
        }
    }


    fun getUsers() = viewModelScope.launch {
        getUsersUseCase().collect { response ->
            _users.value = response
            for (i in response){
            }
        }
    }


    fun getRooms() = viewModelScope.launch {
        getRoomsUseCase().collect { response ->
            _rooms.value = response
            for (i in response){
            }
        }
    }


    suspend fun updateEvent(event: Event){
        updateEventUseCase.updateRoom(event)
    }


    suspend fun deleteEvent(eventID: String){
        deleteEventUseCase.deleteEvent(eventID)
    }


    suspend fun addEvent(event: Event) {
        viewModelScope.launch {
            firebaseRepository.addEventToFirestore(event)
        }
    }


    init {
        getUsers()
        getRooms()
        getEvents()
    }



}