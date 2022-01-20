package com.sachin_sapkale_android_challenge.remote

import com.sachin_sapkale_android_challenge.HitsResponse
import com.sachin_sapkale_android_challenge.remote.source.RemoteDataSource
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {

    @GET("api/?key=25156053-5d02c0f43cdccc3041f2a74da&q=yellow+flowers&image_type=photo&pretty=true")
    suspend fun getSearchList(): Response<HitsResponse>

    //    companion object {
//        var retrofitService: RetrofitService? = null
//        fun getInstance() : RetrofitService {
//            if (retrofitService == null) {
//                val retrofit = Retrofit.Builder()
//                    .baseUrl("https://pixabay.com/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//                retrofitService = retrofit.create(RetrofitService::class.java)
//            }
//            return retrofitService!!
//        }
//    }
    companion object {
        fun getInstance(): RetrofitService {
            return RemoteDataSource.instance.retrofit.create(RetrofitService::class.java)
        }
    }
}