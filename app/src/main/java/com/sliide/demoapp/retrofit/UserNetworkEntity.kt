package com.sliide.demoapp.retrofit

import com.google.gson.annotations.SerializedName

class UserNetworkEntity(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("status") val status: String,
)