package com.maximlysik.domain.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.UUID

data class Room(
    @SerializedName("name") val name: String? = null,
    @SerializedName("roomId") val roomId: String? = null,
    @SerializedName("number") val number: String? = null,
    @SerializedName("floor") val floor: String? = null,
    @SerializedName("capacity") val capacity: String? = null,
    @SerializedName("display_presence") val display_presence: Boolean? = false,
    @SerializedName("speakers_presence") val speakers_presence: Boolean? = false
    ): Serializable



