package com.maximlysik.myapplication.screens.new_event

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximlysik.domain.entities.Event
import com.maximlysik.domain.entities.Room
import com.maximlysik.domain.entities.User
import com.maximlysik.domain.repositories.FirebaseRepository
import com.maximlysik.domain.usecases.GetEventsUseCase
import com.maximlysik.domain.usecases.GetRoomsUseCase
import com.maximlysik.domain.usecases.GetUsersUseCase
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class NewEventViewModel@Inject constructor(
    private val getRoomsUseCase: GetRoomsUseCase,
    private val firebaseRepository: FirebaseRepository,
    private val getUsersUseCase: GetUsersUseCase,
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {

    var items: String = ""


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
    fun setStartTime(time:Date){
        _startTime.value = time
    }

    // VARIABLE FOR CHOSEN START TIME
    var _endTime = MutableLiveData<Date>()
    var endTime: LiveData<Date> = _endTime
    fun setEndTime(time:Date){
        _endTime.value = time
    }

    // VARIABLE FOR CHOSEN ROOM
    var _chosenRoom = MutableLiveData<String>()
    var chosenRoom: LiveData<String> = _chosenRoom
    fun setChosenRoom(room:String){
        _chosenRoom.value = room
    }




    suspend fun addEvent(event: Event) {
        viewModelScope.launch {
            firebaseRepository.addEventToFirestore(event)
        }
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


    init {
        getUsers()
        getRooms()
        getEvents()
    }


}