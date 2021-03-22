package com.devforfun.sliidetask.services.model


import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("data")
    val data: List<User> = listOf(),
    @SerializedName("meta")
    val meta: Meta = Meta()
)