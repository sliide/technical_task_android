package ru.santaev.techtask.network.api

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.santaev.techtask.network.entities.UsersResponse

interface UserApiService {

    @GET("public/v1/users/")
    suspend fun getUsers(@Query("page") page: Int?): UsersResponse

    @POST("public/v1/users")
    suspend fun createUser(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("gender") gender: String,
        @Query("status") status: String
    )
}