package com.sliide.demoapp.di

import com.sliide.demoapp.BuildConfig
import com.sliide.demoapp.repository.MainRepository
import com.sliide.demoapp.retrofit.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *  Module responsible with providing all api communication facilities with an Application Scope.
 */
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val BASE_URL = "https://gorest.co.in/public/v2/"
    private const val TOKEN = "fad8b467dfa723775bb6b90d60c348c138054426f4e32dbb1eb623bdc53c1160"

    @Singleton
    @Provides
    fun provideAuthInterceptor() = Interceptor { chain ->
        val request: Request = chain.request()
        val url = request.url().newBuilder().build()

        val requestBuilder = request
            .newBuilder()
            .addHeader("Authorization", "Bearer $TOKEN")
            .url(url)

        chain.proceed(requestBuilder.build())
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, authInterceptor: Interceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

}