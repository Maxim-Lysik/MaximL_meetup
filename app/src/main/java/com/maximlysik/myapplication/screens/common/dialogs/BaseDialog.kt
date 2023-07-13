package com.maximlysik.myapplication.screens.common.dialogs

import androidx.fragment.app.DialogFragment
import com.maximlysik.myapplication.di.presentation.PresentationModule
import com.maximlysik.myapplication.screens.common.activities.BaseActivity


open class BaseDialog: DialogFragment() {

    private val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.newPresentationComponent(
            PresentationModule(this)
        )
    }

    protected val injector get() = presentationComponent
}