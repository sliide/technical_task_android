package com.slide.test.core.errors

class EmailAlreadyExistsException(code: Long, errors: List<ApiError>) : GoRestApiException(code, errors)
