package com.hanna.sliidetest.data.api

import com.hanna.sliidetest.data.dto.UserDto
import com.hanna.sliidetest.data.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UsersApi {

    @GET("/public/v2/users")
    fun getUsers(
        @Query("page") pageNumber: Int = 1
    ): Flow<ApiResponse<List<UserDto>>>

    @GET("/public/v2/users")
    fun getMeta(): Flow<ApiResponse<List<UserDto>>>

    @POST("/public/v2/users")
    fun addUser(@Body user: UserDto): Flow<ApiResponse<UserDto>>
}