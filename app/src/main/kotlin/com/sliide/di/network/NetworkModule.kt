package com.sliide.di.network

import com.sliide.data.BuildConfig
import com.sliide.data.rest.RetrofitServicesProvider
import com.sliide.data.rest.ServicesProvider
import com.sliide.data.rest.TokenProvider
import com.sliide.data.rest.UrlProvider
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun provideTokenProvider(): TokenProvider = object : TokenProvider {
        override val token: String = BuildConfig.GO_REST_TOKEN

        override val tokenType = "Bearer"
    }

    @Provides
    fun provideUrlProvider(): UrlProvider = object : UrlProvider {
        override val apiUrl: String = BuildConfig.GO_REST_URL
    }

    @Provides
    fun provideServicesProvider(
        url: UrlProvider,
        token: TokenProvider
    ): ServicesProvider = RetrofitServicesProvider(url, token)
}