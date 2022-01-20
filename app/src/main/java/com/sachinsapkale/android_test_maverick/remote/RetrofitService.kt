package com.android_test_maverick.remote

import com.android_test_maverick.RootResponse
import com.android_test_maverick.remote.source.RemoteDataSource
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("public/v1/users?")
    suspend fun getSearchList(@Query("page") pageNumber : Int): Response<RootResponse>

    @GET("public/v1/users?")
    suspend fun createNewUser(@Query("access-token") token : String): Response<RootResponse>

    @DELETE("public/v1/users/{user_id}/?")
    suspend fun deleteUser(@Path("user_id") userId: Int, @Query("access-token") token : String): Response<RootResponse>

    companion object {
        fun getInstance(): RetrofitService {
            return RemoteDataSource.instance.retrofit.create(RetrofitService::class.java)
        }
    }
}