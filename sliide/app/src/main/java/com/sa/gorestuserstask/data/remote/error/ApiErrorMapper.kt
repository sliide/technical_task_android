package com.sa.gorestuserstask.data.remote.error

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ApiErrorMapper(private val gson: Gson) {

    fun toDomainError(throwable: Throwable) = when (throwable) {
        is ApiException -> Error.ApiError(
            code = throwable.httpCode,
            errors = throwable.errorBody?.let { body ->
                val type = object : TypeToken<List<Error.ErrorDetails>>() {}.type
                gson.fromJson(body.string(), type)
            } ?: listOf(),
        )
        else -> Error.OtherError(throwable)
    }
}
