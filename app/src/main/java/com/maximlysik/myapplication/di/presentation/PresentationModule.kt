package com.maximlysik.myapplication.di.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.savedstate.SavedStateRegistryOwner
import com.maximlysik.myapplication.di.activity.ActivityScope
import dagger.BindsInstance
import dagger.Module
import dagger.Provides

@Module
class PresentationModule(private val savedStateRegistryOwner: SavedStateRegistryOwner) {

    @Provides
    fun savedStateRegistryOwner() = savedStateRegistryOwner


}