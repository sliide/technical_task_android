package com.devforfun.sliidetask.services.model


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("pagination")
    val pagination: Pagination = Pagination()
)