package com.sliide.demoapp.utils.mapper

class ResponseCodeMapper {
    companion object {
        fun map(code: Int): String {
            return when (code) {
                200 -> "Success"
                201 -> "Resource was successfully created"
                204 -> "Request was handled successfully with no body"
                304 -> "The resource was not modified"
                400 -> "Bad request"
                401 -> "Authentication failed"
                403 -> "The authenticated user is not allowed to access API"
                404 -> "The requested resource does not exist"
                405 -> "Method not allowed"
                415 -> "Unsupported media type"
                422 -> "Data validation failed"
                429 -> "Too many requests"
                500 -> "Internal server error"
                else -> "Something went wrong!"
            }
        }
    }
}