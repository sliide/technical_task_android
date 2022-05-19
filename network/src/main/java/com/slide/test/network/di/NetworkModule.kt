package com.slide.test.network.di

import com.slide.test.core.NetworkConfig
import com.slide.test.network.GoRestAuthInterceptor
import com.slide.test.network.service.UsersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    internal fun provideUsersService(
        networkConfig: NetworkConfig,
        okHttpClient: OkHttpClient
    ): UsersService {
        return Retrofit.Builder()
            .baseUrl(networkConfig.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(UsersService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        goRestAuthInterceptor: GoRestAuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(goRestAuthInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    internal fun providesAuthInterceptor(networkConfig: NetworkConfig) : GoRestAuthInterceptor {
            return GoRestAuthInterceptor(networkConfig.accessToken)
    }

    @Provides
    @Singleton
    internal fun providesLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }
    }
}