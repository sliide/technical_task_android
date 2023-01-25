package com.sliide.data.users

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
    val gender: String,
    val status: String
)