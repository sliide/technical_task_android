package com.hanna.sliidetest.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Assuming I have a  contract with BE, meta will never be null,
 * and lists, if no data will return an empty list.
 */
data class UsersResponse(
    @SerializedName("meta") val meta: MetaDto,
    @SerializedName("data") val data: List<UserDto>
)