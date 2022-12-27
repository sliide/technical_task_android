package com.hanna.sliidetest.domain.usecases

import com.hanna.sliidetest.data.network.Resource
import com.hanna.sliidetest.data.repositories.repositories.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val repository: UsersRepository) {

    suspend operator fun invoke(userId: Int): Flow<Resource<Unit>> {
        return repository.deleteUser(userId)
    }
}