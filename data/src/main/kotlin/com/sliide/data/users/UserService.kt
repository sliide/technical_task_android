package com.sliide.data.users

import com.sliide.data.Page
import com.sliide.data.retrofit.Result
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @GET("users")
    suspend fun users(@Query("page") @Page page: Int): Result<List<UserDto>>

    @DELETE("users/{user_id}")
    suspend fun delete(@Path("user_id") @UserId userId: Int): Result<Unit>

    @POST("users")
    suspend fun create(@Body create: CreateUserDto): Result<UserDto>
}