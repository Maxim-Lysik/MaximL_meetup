package com.maximlysik.myapplication.screens.rooms

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.maximlysik.domain.entities.Room
import com.maximlysik.myapplication.R

class RoomsAdapter(
    val ctx: Context, private val items: List<Room>
) : RecyclerView.Adapter<RoomsAdapter.RoomItemsViewHolder>() {

    private lateinit var roomsList: List<Room>

    inner class RoomItemsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val room_name: TextView = view.findViewById(R.id.room_number)
        val room_number: TextView = view.findViewById(R.id.room_floor)

    }

    fun bindData(hotelItems: List<Room>) {
        roomsList = ArrayList(hotelItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomItemsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.room_item, parent, false)
        return RoomItemsViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: RoomItemsViewHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.room_name.text = roomsList[position].name.toString()
        holder.room_number.text = roomsList[position].number.toString()

        // Передаем нашу аудиторию
        holder.itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var bundle = Bundle()
                val passedRoom: Room = roomsList[position]
                bundle.putSerializable("our_room", passedRoom)
                val activity = ctx as AppCompatActivity
                activity.findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_navigation_rooms_to_separateRoomFragment, bundle)
            }
        })

    }


    override fun getItemCount(): Int {
        return roomsList.size
    }


}