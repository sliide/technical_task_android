package com.slide.test.network.model

/**
 * Created by Stefan Halus on 18 May 2022
 */
data class UserDto(
    val id: Long,
    val name: String,
    val email: String,
    val gender: String,
    val status: String
)
