package com.sa.gorestuserstask.data.remote.error

sealed class Error {

    object GeneralError: Error()

    data class ApiError(
        val code: Int,
        val errors: List<ErrorDetails> = listOf()
    ) : Error()

    data class ErrorDetails(
        val field: String = "",
        val message: String = "",
    )

    data class OtherError(
        val cause: Throwable
    ) : Error()
}
