package com.devforfun.sliidetask.services;

import com.devforfun.sliidetask.services.events.Result
import com.devforfun.sliidetask.services.model.*

import io.reactivex.Single


interface UsersService {

    fun fetchUsers(): Single<Result<List<User>>>

    fun createUser(user: User): Single<Result<User>>

    fun deleteUser(userId: Int): Single<Result<Int>>

}