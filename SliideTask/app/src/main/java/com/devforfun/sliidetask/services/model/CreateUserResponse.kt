package com.devforfun.sliidetask.services.model


import com.google.gson.annotations.SerializedName

data class CreateUserResponse(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("url")
    val url: String = ""
)