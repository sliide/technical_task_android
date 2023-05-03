package com.devforfun.sliidetask.services.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int=0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("gender")
    val gender: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("status")
    val status : String =""
)
