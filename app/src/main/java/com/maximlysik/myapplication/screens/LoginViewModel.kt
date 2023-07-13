package com.maximlysik.myapplication.screens

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maximlysik.domain.entities.User
import com.maximlysik.domain.repositories.FirebaseRepository
import com.maximlysik.domain.usecases.GetRoomsUseCase
import com.maximlysik.myapplication.screens.common.dialogs.DialogsNavigator
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val getRoomsUseCase: GetRoomsUseCase,
    private val dialogsNavigator: DialogsNavigator,
    private val firebaseRepository: FirebaseRepository
): ViewModel() {

    suspend fun addUser(user: User) {

        viewModelScope.launch {
            firebaseRepository.addUserToFirestore(user)
            }
        }


    init {
        getRooms()
    }

    private fun getRooms() = viewModelScope.launch {
        getRoomsUseCase().collect { response ->
            var booksResponse = response
        }
    }

    }

