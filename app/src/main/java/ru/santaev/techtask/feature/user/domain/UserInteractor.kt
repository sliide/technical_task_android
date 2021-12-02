package ru.santaev.techtask.feature.user.domain

import ru.santaev.techtask.network.entities.UserEntity
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun getUsers(): List<UserEntity> {
        val response = userRepository.getUsers(page = null)
        return userRepository.getUsers(page = response.meta.pagination.pages).data
    }

    suspend fun createUser(name: String, email: String) {
        return userRepository.createUser(name, email)
    }
}
