package com.maximlysik.domain.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("login") val login: String? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("password") val chosenForMeeting: Boolean? = false
) : Serializable
