package com.devforfun.sliidetask.services.model

import com.google.gson.annotations.SerializedName

data class UserBody(
    @SerializedName("name")
    val name: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("status")
    val status: String
)
