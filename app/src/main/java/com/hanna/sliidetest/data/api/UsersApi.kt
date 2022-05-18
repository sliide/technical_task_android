package com.hanna.sliidetest.data.api

import com.hanna.sliidetest.data.dto.UsersResponse
import com.hanna.sliidetest.data.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {

    @GET("/public/v1/users")
    fun getUsers(
        @Query("page") pageNumber: Int = 1
    ): Flow<ApiResponse<UsersResponse>>

    @GET("/public/v1/users")
    fun getMeta(): Flow<ApiResponse<UsersResponse>>
}