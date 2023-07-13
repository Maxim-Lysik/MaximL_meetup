package com.maximlysik.myapplication.screens.add_room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximlysik.domain.entities.Room
import com.maximlysik.domain.repositories.FirebaseRepository
import com.maximlysik.domain.usecases.GetRoomsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddRoomViewModel @Inject constructor(
    private val getRoomsUseCase: GetRoomsUseCase,
    private val firebaseRepository: FirebaseRepository
): ViewModel() {



    suspend fun addRoom(room: Room) {
        viewModelScope.launch {
            firebaseRepository.addRoomToFirestore(room)
        }
    }




}


