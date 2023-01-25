package com.sliide.data.rest

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sliide.data.BuildConfig
import com.sliide.data.retrofit.ResultAdapterFactory
import com.sliide.data.users.UserService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

class RetrofitServicesProvider @Inject constructor(
    private val url: UrlProvider,
    private val token: TokenProvider
) : ServicesProvider {

    private val mimeTypeJson = "application/json".toMediaType()
    private val json = Json { ignoreUnknownKeys = true }
    
    private val users: UserService by lazy {
        retrofit(okHttClient()).create()
    }

    private fun retrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder().apply {
        baseUrl(url.apiUrl)
        client(okHttpClient)
        addCallAdapterFactory(ResultAdapterFactory())
        addConverterFactory(json.asConverterFactory(mimeTypeJson))
    }.build()

    private fun okHttClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token))

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                level =
                    HttpLoggingInterceptor.Level.BASIC
            }
            builder.addInterceptor(logging)
        }

        return builder.build()
    }

    override fun provideUsersService(): UserService {
        return users
    }
}