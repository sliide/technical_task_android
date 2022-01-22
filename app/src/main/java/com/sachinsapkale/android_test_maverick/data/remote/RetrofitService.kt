package com.android_test_maverick.remote

import com.android_test_maverick.CreateUserResponse
import com.android_test_maverick.RootResponse
import com.android_test_maverick.UserModel
import com.android_test_maverick.remote.source.RemoteDataSource
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {

    @GET("public/v1/users?")
    suspend fun getUserListFromLastPage(@Query("page") pageNumber : Int): Response<RootResponse>

    @POST("public/v1/users?")
    suspend fun createNewUser(@Query("access-token") token : String,@Body user: UserModel): Response<CreateUserResponse>

    @DELETE("public/v1/users/{user_id}/?")
    suspend fun deleteUser(@Path("user_id") userId: Int, @Query("access-token") token : String): Response<RootResponse>

    companion object {
        fun getInstance(): RetrofitService {
            return RemoteDataSource.instance.retrofit.create(RetrofitService::class.java)
        }
    }
}