package com.slide.test.usecase.users

import com.slide.test.repository.UsersRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

/**
 * Created by Stefan Halus on 20 May 2022
 */
interface DeleteUserUseCase {

    fun execute(userId: Long): Completable
}

internal class DeleteUserUseCaseImplementation @Inject constructor(
    private val usersRepository: UsersRepository
) : DeleteUserUseCase {

    override fun execute(userId: Long): Completable {
        return usersRepository.deleteUser(userId)
    }

}