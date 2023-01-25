package com.sliide.data.users

import kotlinx.serialization.Serializable

@Serializable
data class FieldErrorDto(
    val field: String,
    val message: String
)