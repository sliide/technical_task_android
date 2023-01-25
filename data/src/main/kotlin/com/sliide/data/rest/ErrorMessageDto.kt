package com.sliide.data.rest

import kotlinx.serialization.Serializable

@Serializable
data class ErrorMessageDto(
    val message: String
)