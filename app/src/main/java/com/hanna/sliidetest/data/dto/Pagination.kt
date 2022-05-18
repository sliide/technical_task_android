package com.hanna.sliidetest.data.dto

import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("total") val total: Int? = null,
    @SerializedName("pages") val pages: Int? = null,
    @SerializedName("page") val page: Int? = null,
    @SerializedName("limit") val limit: Int? = null
)