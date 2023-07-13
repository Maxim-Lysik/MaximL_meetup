package com.maximlysik.myapplication.screens.common

import javax.inject.Inject
import com.maximlysik.domain.repositories.ScreensNavigator
import com.maximlysik.myapplication.screens.rooms.RoomsFragment
import com.maximlysik.myapplication.screens.calendar.CalendarFragment
import com.maximlysik.myapplication.screens.events.UserEventsFragment

class ScreensNavigationImplementation @Inject constructor(private val activity: androidx.appcompat.app.AppCompatActivity):
    ScreensNavigator {

    override fun navigateBack() {
        activity.onBackPressed()
    }

    override fun toHomeFragment() {
        activity.supportFragmentManager.beginTransaction().add(CalendarFragment(),null).commitAllowingStateLoss()
    }

    override fun toDashboardFragment() {
        activity.supportFragmentManager.beginTransaction().add(RoomsFragment(),null).commitAllowingStateLoss()
    }

    override fun toNatificationsFragment() {
        activity.supportFragmentManager.beginTransaction().add(UserEventsFragment(),null).commitAllowingStateLoss()
    }



}