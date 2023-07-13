package com.maximlysik.myapplication.di.app

import com.maximlysik.data.di.FirebaseModule
import com.maximlysik.myapplication.di.activity.ActivityComponent
import dagger.Component


@AppScope
@Component(modules = [AppModule::class, FirebaseModule::class])
interface AppComponent {

    fun newActivityComponentBuilder(): ActivityComponent.Builder


}