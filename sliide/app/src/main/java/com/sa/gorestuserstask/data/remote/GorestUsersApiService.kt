package com.sa.gorestuserstask.data.remote

import retrofit2.Response
import retrofit2.http.*

interface GorestUsersApiService {

    @GET("$VERSION/users")
    suspend fun getUsers(@Query("page") pageNumber: Int? = null): UserApiResponse

    @POST("$VERSION/users")
    suspend fun addUser(@Body user: UserApiRequest): AddUserApiResponse

    @DELETE("$VERSION/users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<Void>

    private companion object {
        private const val VERSION = "v1"
    }
}
