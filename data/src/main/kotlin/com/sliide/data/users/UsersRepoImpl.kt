package com.sliide.data.users

import com.sliide.boundary.users.User
import com.sliide.boundary.users.UsersRepo
import com.sliide.data.IoDispatcher
import com.sliide.data.rest.ServicesProvider
import com.sliide.data.retrofit.asSuccess
import com.sliide.data.retrofit.isSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepoImpl @Inject constructor(
    private val services: ServicesProvider,
    @IoDispatcher
    private val io: CoroutineDispatcher
) : UsersRepo {

    private val userServices by lazy {
        services.provideUsersService()
    }

    override suspend fun users(page: Int): List<User> = withContext(io) {
        val result = userServices.users(page)

        if (result.isSuccess()) {
            result.asSuccess().value.map { userDto -> userDto.toUser() }
        } else {
            throw result.toLoadPageException(page)
        }
    }

    override suspend fun create(name: String, email: String): User = withContext(io) {
        val gender = "male" // TODO ask about def value
        val status = "1active" // TODO ask about def active
        val body = CreateUserDto(name, email, gender, status)
        val result = userServices.create(body)

        if (result.isSuccess()) {
            result.value.toUser()
        } else {
            throw result.toCreateException(name, email)
        }
    }

    override suspend fun delete(userId: Int) = withContext(io) {
        val result = userServices.delete(userId)
        if (!result.isSuccess()) {
            throw result.toDeleteException(userId)
        }
    }
}