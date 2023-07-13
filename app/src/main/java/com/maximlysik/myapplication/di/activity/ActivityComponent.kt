package com.maximlysik.myapplication.di.activity

import androidx.appcompat.app.AppCompatActivity
import com.maximlysik.myapplication.di.presentation.PresentationComponent
import com.maximlysik.myapplication.di.presentation.PresentationModule
import dagger.Subcomponent



@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun newPresentationComponent(presentationModule: PresentationModule): PresentationComponent

    @Subcomponent.Builder
    interface Builder {
        @dagger.BindsInstance
        fun activity(activity: AppCompatActivity): Builder
        fun build(): ActivityComponent
    }
    

}


