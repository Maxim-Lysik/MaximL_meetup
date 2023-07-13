package com.maximlysik.data.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Scope
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    fun provideMoviesFirebaseDatabase(): FirebaseFirestore =
        FirebaseFirestore.getInstance()

    @Named("Users")
    @Provides
    fun provideFirebaseDatabaseUsers(): CollectionReference =
        Firebase.firestore.collection("Users")

    @Named("Events")
    @Provides
    fun provideFirebaseDatabaseEvents(): CollectionReference =
        Firebase.firestore.collection("Events")

    @Named("Rooms")
    @Provides
    fun provideFirebaseDatabaseRooms(): CollectionReference =
        Firebase.firestore.collection("Rooms")


}



