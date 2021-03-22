package com.devforfun.sliidetask.services.model


import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("limit")
    val limit: Int = 0,
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("pages")
    val pages: Int = 0,
    @SerializedName("total")
    val total: Int = 0
)