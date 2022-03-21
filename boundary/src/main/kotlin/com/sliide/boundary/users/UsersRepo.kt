package com.sliide.boundary.users

import kotlin.jvm.Throws

interface UsersRepo {

    @Throws(LoadPageFailed::class)
    suspend fun users(page: Int): List<User>

    @Throws(CreateUserFailed::class, FieldsProblem::class)
    suspend fun create(name: String, email: String): User

    @Throws(DeleteUserFailed::class)
    suspend fun delete(userId: Int)
}