package com.maximlysik.myapplication.screens.events

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
import com.maximlysik.domain.entities.Event
import com.maximlysik.domain.entities.Room
import com.maximlysik.myapplication.R


class EventsAdapter(
    val ctx: Context, private val items: List<Event>
) : RecyclerView.Adapter<EventsAdapter.EventItemsViewHolder>() {

    private lateinit var eventsList: List<Event>

    inner class EventItemsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val event_title: TextView = view.findViewById(R.id.event_title)
        val event_room: TextView = view.findViewById(R.id.event_room)


    }

    fun bindData(hotelItems: List<Event>) {
        eventsList = ArrayList(hotelItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItemsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item, parent, false)
        return EventItemsViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: EventItemsViewHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.event_title.text = eventsList[position].name.toString()
        holder.event_room.text = eventsList[position].room.toString()

        // Передаем нашу аудиторию
        holder.itemView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                var bundle = Bundle()
                val passedEvent: Event = eventsList[position]
                bundle.putSerializable("our_event", passedEvent)
                val activity = ctx as AppCompatActivity
                activity.findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_navigation_user_events_to_separateEventFragment, bundle)
            }
        })

    }


    override fun getItemCount(): Int {
        return eventsList.size
    }


}