package com.sliide.domain.users

import com.sliide.interactor.users.EmailErrors
import java.util.regex.Pattern
import javax.inject.Inject

class ValidateEmailCaseImpl @Inject constructor() : ValidateEmailCase {

    private companion object {
        private val emailPattern by lazy {
            Pattern.compile(
                "[a-zA-Z0-9+._%\\-]{1,256}" +
                        "@" +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                        "(" +
                        "\\." +
                        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                        ")+"
            )
        }
    }

    override fun validate(email: String): EmailErrors {
        val cleaned = email.trim()

        return when {
            cleaned.isEmpty() -> EmailErrors.EMAIL_REQUIRED
            !emailPattern.matcher(cleaned).matches() -> EmailErrors.INCORRECT_FORMAT
            else -> EmailErrors.NONE
        }
    }
}