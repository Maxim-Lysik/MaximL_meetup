package com.maximlysik.domain.usecases

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.maximlysik.domain.entities.Event
import com.maximlysik.domain.repositories.FirebaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject




class AddEventUseCase @Inject constructor(private val context: Context, private val acivity: AppCompatActivity, private val firebaseRepository: FirebaseRepository) {


    suspend fun addEvent(event: Event) {
        withContext(Dispatchers.IO) {
            firebaseRepository.addEventToFirestore(event)
        }
    }



}