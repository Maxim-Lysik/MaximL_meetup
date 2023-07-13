package com.maximlysik.data.di

import android.app.Activity
import dagger.Subcomponent


@Subcomponent(
    modules = [FirebaseModule::class]
)
interface FirebaseComponent {
    fun inject(loginActivity: Activity)
}
