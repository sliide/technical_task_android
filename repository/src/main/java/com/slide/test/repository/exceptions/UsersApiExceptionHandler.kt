package com.slide.test.repository.exceptions

import com.slide.test.core.errors.EmailAlreadyExistsException
import com.slide.test.core.errors.GoRestApiException
import javax.inject.Inject

interface UsersApiExceptionHandler {

    fun handleException(throwable: Throwable): Throwable
}

internal class UsersApiExceptionHandlerImplementation @Inject constructor() : UsersApiExceptionHandler {

    override fun handleException(throwable: Throwable): Throwable {
        return when (throwable) {
            is GoRestApiException -> handleGoRestException(throwable)
            else -> throwable
        }
    }

    private fun handleGoRestException(exception: GoRestApiException): Exception {
        return when (exception.code) {
            422L -> {
                val emailAlreadyExists = exception.errors.firstOrNull {
                    //Ideally the API should return a unique error code per error
                    //Error handling should not rely on error raw messages
                    it.field == "email" && it.message == "has already been taken"
                }
                if (emailAlreadyExists != null) {
                    EmailAlreadyExistsException(exception.code, exception.errors)
                } else {
                    exception
                }
            }
            //TODO(Handle the rest of the exceptions)
            else -> exception
        }
    }

}