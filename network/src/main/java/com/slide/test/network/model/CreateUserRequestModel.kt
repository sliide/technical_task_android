package com.slide.test.network.model

data class CreateUserRequestDto(
    val name: String,
    val email: String,
    val gender: String,
    val status: String
)