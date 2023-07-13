package com.maximlysik.myapplication.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maximlysik.domain.repositories.FirebaseRepository
import com.maximlysik.domain.usecases.AddEventUseCase
import com.maximlysik.domain.usecases.DeleteEventUseCase
import com.maximlysik.domain.usecases.DeleteRoomUseCase
import com.maximlysik.domain.usecases.GetEventsUseCase
import com.maximlysik.domain.usecases.GetRoomsUseCase
import com.maximlysik.domain.usecases.GetUsersUseCase
import com.maximlysik.domain.usecases.UpdateEventUseCase
import com.maximlysik.domain.usecases.UpdateRoomUseCase
import com.maximlysik.myapplication.screens.add_room.AddRoomViewModel
import com.maximlysik.myapplication.screens.common.dialogs.DialogsNavigator
import com.maximlysik.myapplication.screens.calendar.CalendarViewModel
import com.maximlysik.myapplication.screens.events.UserEventsViewModel
import com.maximlysik.myapplication.screens.new_event.NewEventViewModel
import com.maximlysik.myapplication.screens.rooms.RoomsViewModel
import com.maximlysik.myapplication.screens.separate_event.SeparateEventViewModel
import com.maximlysik.myapplication.screens.separate_room.SeparateRoomViewModel
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Provider

class OurViewModelFactory @Inject constructor(
    private val deleteRoomUseCaseProvider: Provider<DeleteRoomUseCase>,
    private val getRoomsUseCaseProvider: Provider<GetRoomsUseCase>,
    private val getUpdateRoomUseCaseProvider: Provider<UpdateRoomUseCase>,
    private val getUsersUseCaseProvider: Provider<GetUsersUseCase>,
    private val getDeleteEventUseCase: Provider<DeleteEventUseCase>,
    private val getUpdateEventUseCase: Provider<UpdateEventUseCase>,
    private val getAddEventUseCase: Provider<AddEventUseCase>,
    private val getEventsUseCaseProvider: Provider<GetEventsUseCase>,
    private val getDialogsNavigatorProvider: Provider<DialogsNavigator>,
    private val firebaseRepositoryImplementationProvider: Provider<FirebaseRepository>

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when (modelClass){
            CalendarViewModel::class.java -> CalendarViewModel(
                getRoomsUseCaseProvider.get(),
                getDialogsNavigatorProvider.get()
            ) as T
            LoginViewModel::class.java -> LoginViewModel(
                getRoomsUseCaseProvider.get(),
                getDialogsNavigatorProvider.get(),
                firebaseRepositoryImplementationProvider.get()
            ) as T
            RoomsViewModel::class.java -> RoomsViewModel(
                getRoomsUseCaseProvider.get(),
                firebaseRepositoryImplementationProvider.get()
            ) as T
            AddRoomViewModel::class.java -> AddRoomViewModel(
                getRoomsUseCaseProvider.get(),
                firebaseRepositoryImplementationProvider.get()
            ) as T
            SeparateRoomViewModel::class.java -> SeparateRoomViewModel(
                getRoomsUseCaseProvider.get(),
                firebaseRepositoryImplementationProvider.get(),
                deleteRoomUseCaseProvider.get(),
                getUpdateRoomUseCaseProvider.get()
            ) as T
            NewEventViewModel::class.java -> NewEventViewModel(
                getRoomsUseCaseProvider.get(),
                firebaseRepositoryImplementationProvider.get(),
                getUsersUseCaseProvider.get(),
                getEventsUseCaseProvider.get()

            ) as T
            UserEventsViewModel::class.java -> UserEventsViewModel(
                getEventsUseCaseProvider.get(),
                firebaseRepositoryImplementationProvider.get()
            ) as T
            SeparateEventViewModel::class.java -> SeparateEventViewModel(
                getEventsUseCaseProvider.get(),
                firebaseRepositoryImplementationProvider.get(),
                getRoomsUseCaseProvider.get(),
                getUsersUseCaseProvider.get(),
                getDeleteEventUseCase.get(),
                getUpdateEventUseCase.get(),
                getAddEventUseCase.get()
            ) as T




            else -> throw RuntimeException("unsupported viemodel type $modelClass")
        }

    }
}

