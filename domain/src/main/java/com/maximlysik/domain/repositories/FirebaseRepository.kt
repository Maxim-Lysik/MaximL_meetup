package com.maximlysik.domain.repositories

import com.maximlysik.domain.entities.Event
import com.maximlysik.domain.entities.Response
import com.maximlysik.domain.entities.Room
import com.maximlysik.domain.entities.User
import kotlinx.coroutines.flow.Flow


/*typealias Books = List<Book>
typealias BooksResponse = Response<Books>
typealias AddUserResponse = Response<Boolean>
typealias DeleteBookResponse = Response<Boolean>
 */


typealias Rooms = List<Room>
typealias RoomsResponse = Response<Rooms>


interface FirebaseRepository {


    fun getRoomsFromFirestore():  Flow<List<Room>>

    fun getUsersFromFirestore():  Flow<List<User>>

    fun getEventsFromFirestore():  Flow<List<Event>>

    suspend fun addUserToFirestore(user: User): Response<Boolean>

    suspend fun addRoomToFirestore(room: Room): Response<Boolean>

    suspend fun addEventToFirestore(event: Event): Response<Boolean>

    suspend fun deleteUserFromFirestore(user: User): Response<Boolean>

    suspend fun deleteRoomFromFirestore(roomId: String)

    suspend fun deleteEventFromFirestore(eventId: String)

    suspend fun updateUserInFirestore(user: User): Response<Boolean>

    suspend fun updateRoomInFirestore(room: Room): Response<Boolean>

    suspend fun updateEventInFirestore(event: Event): Response<Boolean>


}