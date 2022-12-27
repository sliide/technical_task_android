/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hanna.sliidetest.data.network

import okhttp3.Headers
import retrofit2.Response

/**
 * Common class used by API responses.
 * Based on the class provided by android architecture-components sample.
 * @param <T> the type of the response object
</T> */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(code: Int, error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(code, error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                //since I have no contract with BE, I may get a response with no body,
                // although the response is successful.
                body?.let {
                    ApiSuccessResponse(it, response.headers())
                } ?: ApiEmptyResponse()
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) response.message() else msg

                ApiErrorResponse(response.code(), errorMsg ?: "unknown error")
            }
        }
    }
}

//response types
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T, val headers: Headers?) : ApiResponse<T>()

data class ApiErrorResponse<T>(val code: Int, val errorMessage: String) : ApiResponse<T>()