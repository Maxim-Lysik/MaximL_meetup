package com.maximlysik.myapplication.di.activity

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.maximlysik.domain.repositories.ScreensNavigator
import com.maximlysik.myapplication.screens.MainActivity
import com.maximlysik.myapplication.screens.common.ScreensNavigationImplementation
import com.maximlysik.myapplication.screens.common.dialogs.DialogsNavigator
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
abstract class ActivityModule {


    @ActivityScope
    @Binds
    abstract fun screensNavigator(screensNavigatorImpl: ScreensNavigationImplementation): ScreensNavigator


    companion object {
        @Provides
        fun layoutInflater(activity: AppCompatActivity) = LayoutInflater.from(activity)

        @Provides
        fun fragmentManager(activity: AppCompatActivity) = activity.supportFragmentManager

        @Provides
        fun provideContext(activity: AppCompatActivity): Context = activity


    }

}