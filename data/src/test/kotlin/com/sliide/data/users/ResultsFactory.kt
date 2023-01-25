package com.sliide.data.users

import com.sliide.data.rest.ErrorMessageDto
import com.sliide.data.retrofit.HttpException
import com.sliide.data.retrofit.Result
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

object ResultsFactory {

    fun successUsers(page: Int, pageSize: Int): Result<List<UserDto>> {
        val users = UsersDtoFactory.createUsers((page - 1) * pageSize, pageSize)
        return Result.Success.HttpResponse(
            value = users,
            statusCode = 200,
            statusMessage = "OK",
            url = "https://url.com"
        )
    }

    fun failureUsers(): Result<List<UserDto>> {
        return Result.Failure.HttpError(
            HttpException(
                statusCode = 500,
                statusMessage = "INTERNAL SERVER ERROR",
                url = "https://url.com"
            )
        )
    }

    fun successCreate(name: String, email: String): Result<UserDto> {
        val user = UsersDtoFactory.createUser(id = 1, name, email)
        return Result.Success.HttpResponse(
            value = user,
            statusCode = 201,
            statusMessage = "OK",
            url = "https://url.com"
        )
    }

    fun emailHasAlreadyTaken(): Result<UserDto> {
        return Result.Failure.HttpError(
            HttpException(
                statusCode = 422,
                statusMessage = "Unprocessable Entity",
                errorBody = Json.encodeToString(
                    serializer(),
                    listOf(
                        FieldErrorDto(
                            field = "email",
                            message = "has already been taken"
                        )
                    )
                ).toByteArray()
            )
        )
    }

    fun creationUnauthorizedResponse(): Result<UserDto> {
        return Result.Failure.HttpError(
            HttpException(
                statusCode = 401,
                statusMessage = "Unauthorized",
                errorBody = Json.encodeToString(
                    serializer(),
                    listOf(ErrorMessageDto(message = "Authentication failed"))
                ).toByteArray()
            )
        )
    }

    fun deleteUnauthorizedResponse(): Result<Unit> {
        return Result.Failure.HttpError(
            HttpException(
                statusCode = 401,
                statusMessage = "Unauthorized",
                errorBody = Json.encodeToString(
                    serializer(),
                    listOf(ErrorMessageDto(message = "Authentication failed"))
                ).toByteArray()
            )
        )
    }
}