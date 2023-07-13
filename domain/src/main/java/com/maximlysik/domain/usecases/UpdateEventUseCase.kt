package com.maximlysik.domain.usecases

import com.maximlysik.domain.entities.Event
import com.maximlysik.domain.entities.Room
import com.maximlysik.domain.repositories.FirebaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class UpdateEventUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    suspend fun updateRoom(event: Event) {
        withContext(Dispatchers.IO) {
            firebaseRepository.updateEventInFirestore(event)
        }
    }

}