package com.android_test_maverick.remote

import com.android_test_maverick.SearchResponse
import com.android_test_maverick.remote.source.RemoteDataSource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("public/v1/users?")
    suspend fun getSearchList(@Query("page") pageNumber : Int): Response<SearchResponse>

    companion object {
        fun getInstance(): RetrofitService {
            return RemoteDataSource.instance.retrofit.create(RetrofitService::class.java)
        }
    }
}