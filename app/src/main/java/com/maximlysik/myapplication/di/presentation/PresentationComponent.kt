package com.maximlysik.myapplication.di.presentation

import com.maximlysik.data.di.FirebaseComponent
import com.maximlysik.data.di.FirebaseModule
import com.maximlysik.myapplication.screens.add_room.AddRoomFragment
import com.maximlysik.myapplication.screens.LoginActivity
import com.maximlysik.myapplication.screens.MainActivity
import com.maximlysik.myapplication.screens.rooms.RoomsFragment
import com.maximlysik.myapplication.screens.calendar.CalendarFragment
import com.maximlysik.myapplication.screens.events.UserEventsFragment
import com.maximlysik.myapplication.screens.new_event.NewEventFragment
import com.maximlysik.myapplication.screens.separate_event.SeparateEventFragment
import com.maximlysik.myapplication.screens.separate_room.SeparateRoomFragment
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [PresentationModule::class, ViewModelModule::class])
interface PresentationComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(calendarFragment: CalendarFragment)
    fun inject(roomsFragment: RoomsFragment)
    fun inject(addRoomFragment: AddRoomFragment)
    fun inject(separateRoomFragment: SeparateRoomFragment)
    fun inject(separateEventFragment: SeparateEventFragment)
    fun inject(newEventFragment: NewEventFragment)
    fun inject(loginActivity: LoginActivity)
    fun inject(userEventsFragment: UserEventsFragment)
    fun inject(firebaseModule: FirebaseModule): FirebaseComponent

}