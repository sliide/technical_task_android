package com.android_test_maverick.remote.source

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RemoteDataSource @Inject constructor() {
    private val API_BASE_URL = "https://gorest.co.in/"
    val retrofit: Retrofit

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)

        println("---retorift")
        retrofit = builder.build()
    }

    companion object {
        var self: RemoteDataSource? = null

        val instance: RemoteDataSource
            get() {
                if (self == null) {
                    synchronized(RemoteDataSource::class.java) {
                        if (self == null) {
                            self = RemoteDataSource()
                            println("---once new")
                        }
                    }
                }
                println("---once")
                return self!!
            }
    }

    fun destroyInstance(){
        self = null
        println("---instance released---")
    }
}