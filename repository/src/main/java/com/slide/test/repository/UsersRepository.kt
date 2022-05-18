package com.slide.test.repository

import com.slide.test.network.service.UsersService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Stefan Halus on 18 May 2022
 */
interface UsersRepository {

    fun getUsers(): Single<String>
}

class UsersRepositoryImplementation @Inject constructor(
    val usersService: UsersService
) : UsersRepository {
    override fun getUsers(): Single<String> {
        return usersService.fetchUsers().map { it.map { it.name }.joinToString(",") }
    }
}