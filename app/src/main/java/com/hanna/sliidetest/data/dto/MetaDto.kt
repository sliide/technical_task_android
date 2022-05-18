package com.hanna.sliidetest.data.dto

import com.google.gson.annotations.SerializedName

data class MetaDto(
    @SerializedName("pagination") val pagination: Pagination? = null
)