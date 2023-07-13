package com.maximlysik.myapplication.screens.events

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximlysik.domain.entities.Event
import com.maximlysik.domain.entities.Room
import com.maximlysik.domain.repositories.FirebaseRepository
import com.maximlysik.domain.usecases.GetEventsUseCase
import com.maximlysik.domain.usecases.GetRoomsUseCase
import com.maximlysik.domain.usecases.GetUsersUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserEventsViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val firebaseRepository: FirebaseRepository,
) : ViewModel() {

    private var _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    fun getEvents(userName: String) = viewModelScope.launch {


        var userEvents: ArrayList<Event> = arrayListOf()
        getEventsUseCase().collect { response ->
            for(i in response){
                if(i.participants!!.contains(userName, ignoreCase = true)){
                    userEvents.add(i)
                }
                _events.value = userEvents.toList()
            }
        }
    }

    init {
    }


}