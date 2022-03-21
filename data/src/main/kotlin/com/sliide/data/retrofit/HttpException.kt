package com.sliide.data.retrofit

class HttpException(
    val statusCode: Int,
    val statusMessage: String? = null,
    val url: String? = null,
    val errorBody: ByteArray? = null,
    cause: Throwable? = null
) : Exception(null, cause)
