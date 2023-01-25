package com.sliide.data.rest

import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor(private val token: TokenProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(AUTH, "${token.tokenType} ${token.token}")
            .build()

        return chain.proceed(request)
    }

    private companion object {
        private const val AUTH = "Authorization"
    }
}