package com.devforfun.sliidetask.services;


import com.devforfun.sliidetask.services.model.*
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface UsersProvider {

    @GET("users")
    fun getUsers(@Query("page") pageNumber: Int = 1): Single<UsersResponse>

    @POST("users")
    fun createUser(@Body user: UserBody): Single<CreateUserResponse>

    @DELETE("users/{userId}")
    fun deleteUser(@Path("userId") userId: Int): Single<DeleteUserResponse>

}