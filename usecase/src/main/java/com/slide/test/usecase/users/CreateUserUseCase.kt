package com.slide.test.usecase.users

import com.slide.test.repository.UsersRepository
import com.slide.test.usecase.users.model.CreateUserRequest
import com.slide.test.usecase.users.model.toModel
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

/**
 * Created by Stefan Halus on 20 May 2022
 */
interface CreateUserUseCase {

    fun execute(createUserRequest: CreateUserRequest): Completable
}

internal class CreateUserUseCaseImplementation @Inject constructor(
    private val usersRepository: UsersRepository
) : CreateUserUseCase {

    override fun execute(createUserRequest: CreateUserRequest): Completable {
        return usersRepository.createUser(createUserRequest.toModel())

    }

}

