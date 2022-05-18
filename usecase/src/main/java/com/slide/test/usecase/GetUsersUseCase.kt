package com.slide.test.usecase

import com.slide.test.repository.UsersRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Stefan Halus on 18 May 2022
 */
interface GetUsersUseCase {
    fun execute(): Single<String>
}

class GetUsersUseCaseImplementation @Inject constructor(
    private val usersRepository: UsersRepository
) : GetUsersUseCase {
    override fun execute(): Single<String> {
        return usersRepository.getUsers()
    }
}