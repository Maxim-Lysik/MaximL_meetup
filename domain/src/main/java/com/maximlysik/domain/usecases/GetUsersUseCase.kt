package com.maximlysik.domain.usecases

import android.content.ContentValues
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.maximlysik.domain.repositories.FirebaseRepository
import java.util.Locale
import javax.inject.Inject





class GetUsersUseCase @Inject constructor(private val context: Context, private val acivity: AppCompatActivity, private val firebaseRepository: FirebaseRepository) {


    operator fun invoke() = firebaseRepository.getUsersFromFirestore()



}