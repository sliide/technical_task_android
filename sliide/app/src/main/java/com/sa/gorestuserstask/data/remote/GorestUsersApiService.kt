package com.sa.gorestuserstask.data.remote

import retrofit2.Response
import retrofit2.http.*

interface GorestUsersApiService {

    @GET("$VERSION/users")
    suspend fun getUsers(@Query("page") pageNumber: Int? = null): Response<UserApiResponse>

    @POST("$VERSION/users")
    suspend fun addUser(@Body user: UserApiRequest): Response<AddUserApiResponse>

    @DELETE("$VERSION/users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<Unit>

    private companion object {
        private const val VERSION = "v1"
    }
}
