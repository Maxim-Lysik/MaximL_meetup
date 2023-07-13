package com.maximlysik.myapplication

import android.app.Application
import com.maximlysik.myapplication.di.app.AppComponent
import com.maximlysik.myapplication.di.app.AppModule
import com.maximlysik.myapplication.di.app.DaggerAppComponent


class MyApplication: Application() {

    public val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }



}