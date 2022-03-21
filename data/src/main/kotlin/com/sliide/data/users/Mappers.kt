package com.sliide.data.users

import com.sliide.boundary.users.*
import com.sliide.data.retrofit.Result
import com.sliide.data.retrofit.asFailure
import com.sliide.data.retrofit.isHttpError
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

internal fun UserDto.toUser(): User = User(id, name, email)

internal fun Result<List<UserDto>>.toLoadPageException(page: Int): Throwable {
    val message = "getting page of user is failed [page: $page] $this"
    val instance = this

    return LoadPageFailed(page, message, instance.asFailure().error)
}

internal fun Result<UserDto>.toCreateException(name: String, email: String): Throwable {
    val message = "creating user is failed [name: $name, email: $email] $this"
    val instance = this

    return if (instance.isHttpError() && instance.statusCode == 422) {
        instance.error.errorBody?.decodeToString()?.let { json ->
            val fields = Json.decodeFromString<List<FieldErrorDto>>(json)
                .associate { error -> Pair(error.field, error.message) }

            FieldsProblem(fields, message, instance.error)
        } ?: CreateUserFailed(message, instance.error)
    } else {
        CreateUserFailed(message, instance.asFailure().error)
    }
}

internal fun Result<Unit>.toDeleteException(userId: Int): Throwable {
    val message = "deleting user is failed [userId: $userId] $this"
    val instance = this

    return DeleteUserFailed(userId, message, instance.asFailure().error)
}
