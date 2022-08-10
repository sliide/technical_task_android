package com.sliide.demoapp.retrofit

import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @GET("users")
    suspend fun getUsers(@Query("page") currentPage: Int): Response<List<UserNetworkEntity>>

    @GET("users")
    suspend fun getPagination(): Response<Unit>

    @POST("users")
    suspend fun addUser(
        @Body user: UserNetworkEntity
    ): Response<Unit>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<Unit>
}