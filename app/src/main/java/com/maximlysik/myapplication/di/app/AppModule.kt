package com.maximlysik.myapplication.di.app

import android.app.Application
import com.google.firebase.firestore.CollectionReference
import com.maximlysik.data.repositories.FirebaseRepositoryImplementation
import com.maximlysik.domain.repositories.FirebaseRepository
import com.maximlysik.myapplication.di.Retrofit1
import com.maximlysik.myapplication.di.Retrofit2
import com.maximlysik.myapplication.networking.UrlProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named


@Module
class AppModule(val application: Application) {





    @AppScope
    @Provides
    fun urlProvider() = UrlProvider()

    @Provides
    fun application() = application




    @Provides
    fun provideFireBaseRepositoryImplementation(
     //   booksRef: CollectionReference
    @Named("Users")  usersRef: CollectionReference,
    @Named("Events")  eventsRef: CollectionReference,
    @Named("Rooms")  roomsRef: CollectionReference,

    ): FirebaseRepository = FirebaseRepositoryImplementation(usersRef, eventsRef, roomsRef)




}
