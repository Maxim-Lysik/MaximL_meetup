package com.maximlysik.myapplication.screens.separate_room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maximlysik.domain.entities.Room
import com.maximlysik.domain.repositories.FirebaseRepository
import com.maximlysik.domain.usecases.DeleteRoomUseCase
import com.maximlysik.domain.usecases.GetRoomsUseCase
import com.maximlysik.domain.usecases.UpdateRoomUseCase
import javax.inject.Inject

class SeparateRoomViewModel @Inject constructor(
    private val getRoomsUseCase: GetRoomsUseCase,
    private val firebaseRepository: FirebaseRepository,
    private val deleteRoomUseCase: DeleteRoomUseCase,
    private val updateRoomUseCase: UpdateRoomUseCase
): ViewModel() {

    private var _separate_room = MutableLiveData<Room>()
    val separate_room: LiveData<Room> = _separate_room


    fun setRoom(room:Room){
        _separate_room.value = room
    }

    suspend fun deleteRoom(roomID: String){
        deleteRoomUseCase.deleteRoom(roomID)
    }

    suspend fun updateRoom(room: Room){
        updateRoomUseCase.updateRoom(room)
    }


}


