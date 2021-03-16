package com.devforfun.sliidetask.services.model


import com.google.gson.annotations.SerializedName

data class DeleteUserResponse(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("url")
    val url: String = ""
)