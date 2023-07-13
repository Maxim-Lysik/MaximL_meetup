package com.maximlysik.domain.usecases

import android.content.ContentValues
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.maximlysik.domain.entities.Room
import com.maximlysik.domain.repositories.FirebaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

class UpdateRoomUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {


    suspend fun updateRoom(room: Room) {
        withContext(Dispatchers.IO) {
            firebaseRepository.updateRoomInFirestore(room)
        }
    }


}