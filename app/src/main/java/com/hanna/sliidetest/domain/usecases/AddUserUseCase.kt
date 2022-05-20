package com.hanna.sliidetest.domain.usecases

import com.hanna.sliidetest.data.network.Resource
import com.hanna.sliidetest.data.repositories.repositories.UsersRepository
import com.hanna.sliidetest.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddUserUseCase @Inject constructor(private val repository: UsersRepository) {

    suspend operator fun invoke(user: User): Flow<Resource<User>> {
        return repository.addUser(user)
    }
}