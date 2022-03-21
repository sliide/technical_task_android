package com.sliide.data.users

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserDto(
    val name: String,
    val email: String,
    val gender: String,
    val status: String
)