package com.sliide.data.retrofit

interface HttpResponse {

    val statusCode: Int

    val statusMessage: String?

    val url: String?
}
