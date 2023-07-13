package com.maximlysik.myapplication.di.presentation

import androidx.lifecycle.ViewModel
import com.maximlysik.myapplication.screens.rooms.RoomsViewModel
import com.maximlysik.myapplication.screens.calendar.CalendarViewModel
import com.maximlysik.myapplication.screens.events.UserEventsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CalendarViewModel::class)
    abstract fun myViewModel(calendarViewModel: CalendarViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoomsViewModel::class)
    abstract fun myViewModel2(roomsViewModel: RoomsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserEventsViewModel::class)
    abstract fun myViewModel3(userEventsViewModel: UserEventsViewModel): ViewModel

}