package com.sliide.boundary.users

class LoadPageFailed(val page: Int, message: String, cause: Throwable?) : Exception(message, cause)

class CreateUserFailed(message: String, cause: Throwable?) : Exception(message, cause)

class FieldsProblem(
    val fields: Map<String, String>,
    message: String,
    cause: Throwable?
) : Exception(message, cause)

class DeleteUserFailed(val userId: Int, message: String, cause: Throwable?) :
    Exception(message, cause)