package com.maximlysik.domain.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Event (
    @SerializedName("eventId") val eventId: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("start_time") val starting_time: String? = null,
    @SerializedName("finish_time") val finish_time: String? = null,
    @SerializedName("participants") val participants: String? = null,
    @SerializedName("room") val room: String? = null
): Serializable


