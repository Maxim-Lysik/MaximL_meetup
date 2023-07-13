package com.maximlysik.myapplication.screens.calendar

import androidx.lifecycle.ViewModel
import com.maximlysik.domain.usecases.GetRoomsUseCase
import com.maximlysik.myapplication.screens.common.dialogs.DialogsNavigator
import javax.inject.Inject


class CalendarViewModel  @Inject constructor(
    private val getRoomsUseCase: GetRoomsUseCase,
    private val dialogsNavigator: DialogsNavigator
): ViewModel() {

    init {
    }

}