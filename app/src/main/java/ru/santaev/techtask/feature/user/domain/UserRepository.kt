package ru.santaev.techtask.feature.user.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.santaev.techtask.network.api.UserApiService
import ru.santaev.techtask.network.entities.UsersResponse
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApiService
) {

    suspend fun getUsers(page: Int?): UsersResponse {
        return withContext(Dispatchers.IO) {
            userApi.getUsers(page)
        }
    }

    suspend fun createUser(name: String, email: String) {
        return withContext(Dispatchers.IO) {
            userApi.createUser(name = name, email = email, gender = "male", status = "active")
        }
    }

    suspend fun deleteUser(userId: Long) {
        return withContext(Dispatchers.IO) {
            userApi.deleteUser(userId)
        }
    }
}