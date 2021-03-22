package com.devforfun.sliidetask.api

import com.devforfun.sliidetask.exceptions.InternalServerErrorException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthenticationInterceptor(private val authToken: String?) : Interceptor {
    companion object {
        const val HTTP_STATUS_UNAUTHORIZED = 401
        const val HTTP_STATUS_FORBIDDEN = 403
        const val HTTP_STATUS_OK = 200
        const val HTTP_STATUS_CREATED = 201
        const val HTTP_STATUS_SERVER_ERROR_BEGINNING = 500
        const val HTTP_STATUS_SERVER_ERROR_END = 599
    }


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val builder = original.newBuilder()
            .header("Authorization", authToken)

        val request = builder.build()

        val response = chain.proceed(request)

        return when(response.code()) {
            //todo add custom exceptions based on error code
            in HTTP_STATUS_SERVER_ERROR_BEGINNING..HTTP_STATUS_SERVER_ERROR_END -> throw InternalServerErrorException()
            else -> {
                response
            }
        }
    }
}