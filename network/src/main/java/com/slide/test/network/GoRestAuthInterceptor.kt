package com.slide.test.network

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

internal class GoRestAuthInterceptor(private val accessToken: String) : Interceptor {

    companion object {
        const val AUTHORIZATION = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        if(authenticationRequired(chain.request().headers)) {
            requestBuilder.header(AUTHORIZATION, "Bearer $accessToken")
        }
        requestBuilder.removeHeader(CustomHeaders.NO_AUTH)

        return chain.proceed(requestBuilder.build())
    }

    private fun authenticationRequired(headers: Headers): Boolean {
        return headers[CustomHeaders.NO_AUTH] == null
    }

}
