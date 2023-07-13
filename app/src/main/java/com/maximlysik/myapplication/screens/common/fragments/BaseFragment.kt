package com.maximlysik.myapplication.screens.common.fragments

import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.maximlysik.domain.entities.User
import com.maximlysik.myapplication.di.presentation.PresentationModule
import com.maximlysik.myapplication.screens.common.activities.BaseActivity
import com.maximlysik.myapplication.screens.common.dialogs.DialogsNavigator
import java.util.Calendar
import javax.inject.Inject


open class BaseFragment: Fragment() {

    @Inject
    lateinit var dialogsNavigator: DialogsNavigator

    private val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.newPresentationComponent(
            PresentationModule(this)
        )
    }


    protected val injector get() = presentationComponent



    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDayStart = 0
    var savedMonthStart = 0
    var savedYearStart = 0
    var savedHourStart = 0
    var savedMinuteStart = 0

    var savedDayFinish = 0
    var savedMonthFinish = 0
    var savedYearFinish = 0
    var savedHourFinish = 0
    var savedMinuteFinish = 0
    var startTimeChosen = false


    open fun getDateTimeCalendar() {
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }

}