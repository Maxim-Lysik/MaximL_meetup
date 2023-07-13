package com.maximlysik.data.repositories

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.toObject
import com.maximlysik.data.Constants.CAPACITY
import com.maximlysik.data.Constants.LOGIN
import com.maximlysik.data.Constants.NAME
import com.maximlysik.domain.entities.Event
import com.maximlysik.domain.entities.Response
import com.maximlysik.domain.entities.Response.*
import com.maximlysik.domain.entities.Room
import com.maximlysik.domain.entities.User
import com.maximlysik.domain.repositories.FirebaseRepository
import com.maximlysik.domain.repositories.Rooms
import com.maximlysik.domain.repositories.RoomsResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class FirebaseRepositoryImplementation @Inject constructor(
    @Named("Users") private val usersRef: CollectionReference,
    @Named("Events") private val eventsRef: CollectionReference,
    @Named("Rooms") private val roomsRef: CollectionReference,

    ) : FirebaseRepository {


    override fun getRoomsFromFirestore(): Flow<List<Room>> = callbackFlow {
        val snapshotListener = roomsRef.orderBy(CAPACITY).addSnapshotListener { snapshot, e ->
            val roomsResponse = if (snapshot != null) {
                val rooms = snapshot.toObjects(Room::class.java)


                Success(rooms)

            } else {
                Failure(e)
            }
            trySend(snapshot!!.toObjects(Room::class.java))
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun getUsersFromFirestore(): Flow<List<User>> = callbackFlow {
        val snapshotListener = usersRef.orderBy(LOGIN).addSnapshotListener { snapshot, e ->
            val usersResponse = if (snapshot != null) {
                val rooms = snapshot.toObjects(User::class.java)
                Success(rooms)
            } else {
                Failure(e)
            }
            trySend(snapshot!!.toObjects(User::class.java))
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun getEventsFromFirestore(): Flow<List<Event>> = callbackFlow {
        val snapshotListener = eventsRef.orderBy(NAME).addSnapshotListener { snapshot, e ->
            val usersResponse = if (snapshot != null) {
                val rooms = snapshot.toObjects(Event::class.java)
                Success(rooms)
            } else {
                Failure(e)
            }
            trySend(snapshot!!.toObjects(Event::class.java))
        }
        awaitClose {
            snapshotListener.remove()
        }
    }


    override suspend fun addUserToFirestore(user: User): Response<Boolean> = try {
        usersRef.add(user)
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun addRoomToFirestore(room: Room): Response<Boolean> = try {
        roomsRef.add(room)
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun addEventToFirestore(event: Event): Response<Boolean> = try {
        eventsRef.add(event)
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }


    override suspend fun deleteUserFromFirestore(user: User): Response<Boolean> = try {
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun deleteRoomFromFirestore(roomId: String) {

        val query = roomsRef.whereEqualTo("roomId", roomId).get()

        query.addOnSuccessListener {
            for (document in it) {
                roomsRef.document(document.id).delete().addOnSuccessListener {
                }
            }
        }
    }

    override suspend fun deleteEventFromFirestore(eventId: String) {
        val query = eventsRef.whereEqualTo("eventId", eventId).get()

        query.addOnSuccessListener {
            for (document in it) {
                eventsRef.document(document.id).delete().addOnSuccessListener {
                }
            }
        }
    }




    override suspend fun updateUserInFirestore(user: User): Response<Boolean> = try {
        //usersRef.add(user)
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun updateRoomInFirestore(room: Room): Response<Boolean> = try {
        val query = roomsRef.whereEqualTo("roomId", room.roomId).get()

        query.addOnSuccessListener {
            for (document in it) {
                roomsRef.document(document.id).set(room).addOnSuccessListener {
                    Log.d(ContentValues.TAG, "ROOM WAS UPDATED");
                }

            }
        }
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

    override suspend fun updateEventInFirestore(event: Event): Response<Boolean> = try {
        val query = eventsRef.whereEqualTo("eventId", event.eventId).get()

        query.addOnSuccessListener {
            for (document in it) {
                eventsRef.document(document.id).set(event).addOnSuccessListener {
                }
            }
        }
        Success(true)
    } catch (e: Exception) {
        Failure(e)
    }

}
