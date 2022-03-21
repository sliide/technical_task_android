package com.sliide.data.rest

import kotlinx.serialization.Serializable

@Serializable
data class ErrorMessage(
    val message: String
)