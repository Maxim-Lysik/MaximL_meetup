package com.maximlysik.myapplication.screens.rooms

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximlysik.domain.entities.Room
import com.maximlysik.domain.repositories.FirebaseRepository
import com.maximlysik.domain.repositories.RoomsResponse
import com.maximlysik.domain.usecases.GetRoomsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomsViewModel @Inject constructor(
    private val getRoomsUseCase: GetRoomsUseCase,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {


    private var _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> = _rooms

    suspend fun addRoom(room: Room) {
        viewModelScope.launch {
            firebaseRepository.addRoomToFirestore(room)
        }
    }

    val roomsList = emptyList<Room>()

    private fun getRooms() = viewModelScope.launch {
        getRoomsUseCase().collect { response ->
            _rooms.value = response
        }


    }


    init {
        getRooms()
    }


    private val _text = MutableLiveData<String>().apply {
        value = "This is Rooms Fragment"
    }
    val text: LiveData<String> = _text


}