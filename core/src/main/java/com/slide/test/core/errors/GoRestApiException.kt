package com.slide.test.core.errors

import java.io.IOException

/**
 * Created by Stefan Halus on 22 May 2022
 */
open class GoRestApiException(
    open val code: Long,
    open val errors: List<ApiError>
) : IOException() {

    override fun getLocalizedMessage(): String? {
        return "Code: $code, details: $errors"
    }
}
