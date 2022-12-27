package com.hanna.sliidetest.di

import com.hanna.sliidetest.data.network.FlowCallAdapterFactory
import com.hanna.sliidetest.BuildConfig
import com.hanna.sliidetest.BuildConfig.API_KEY
import com.hanna.sliidetest.data.api.*
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

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private fun provideAuthInterceptor(
    ): Interceptor {
        return Interceptor { chain ->
            val request: Request = chain.request()
            val url = request
                .url
                .newBuilder()
                .build()

            val requestBuilder = request
                .newBuilder()
                .url(url)
                .addHeader("Authorization", "Bearer $API_KEY")

            chain.proceed(requestBuilder.build())
        }
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(provideAuthInterceptor())
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(FlowCallAdapterFactory())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun providesUsersApi(retrofit: Retrofit): UsersApi = retrofit.create(UsersApi::class.java)
}