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
}