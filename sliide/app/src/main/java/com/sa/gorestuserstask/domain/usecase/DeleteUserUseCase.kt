package com.sa.gorestuserstask.domain.usecase

import com.sa.gorestuserstask.domain.repository.UserRepository

class DeleteUserUseCase(private val repository: UserRepository) : UseCase<Int, Unit> {
    override suspend fun invoke(input: Int): Output<Unit> = repository.deleteUser(input)
}
