package com.slide.test.network.model

/**
 * Created by Stefan Halus on 19 May 2022
 */
data class ResponseDto<T>(
    val code: Long,
    val data: T
)
