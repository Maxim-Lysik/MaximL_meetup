package com.maximlysik.domain.usecases

import android.content.ContentValues
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.maximlysik.domain.repositories.FirebaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

class DeleteRoomUseCase @Inject constructor(private val context: Context, private val acivity: AppCompatActivity, private val firebaseRepository: FirebaseRepository) {



    suspend fun deleteRoom(roomID: String){
        withContext(Dispatchers.IO) {
            firebaseRepository.deleteRoomFromFirestore(roomID)
        }}



    operator fun invoke() = firebaseRepository.getRoomsFromFirestore()



}