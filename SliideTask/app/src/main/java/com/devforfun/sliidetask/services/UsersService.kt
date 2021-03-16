package com.devforfun.sliidetask.services;

import com.devforfun.sliidetask.repository.Result
import com.devforfun.sliidetask.services.model.*

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Query


interface UsersService {

    fun fetchUsers(): Single<Result<List<User>>>

    fun createUser(user: User): Single<Result<User>>

    fun deleteUser(userId: Int): Single<Result<Int>>

}