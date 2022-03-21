package com.sliide.data.users.create

import com.sliide.domain.users.create.ValidateEmailCase
import com.sliide.domain.users.create.ValidateEmailCaseImpl
import com.sliide.interactor.users.create.EmailErrors
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ValidateEmailCaseTests(
    private val email: String,
    private val error: EmailErrors
) {

    companion object {

        @Parameterized.Parameters(name = "{index}: email: {0} error:{1}")
        @JvmStatic
        fun params(): Collection<Array<Any>> {
            return listOf(
                arrayOf("mail@mail.com", EmailErrors.NONE),
                arrayOf("mail123@mail.com", EmailErrors.NONE),
                arrayOf("", EmailErrors.EMAIL_REQUIRED),
                arrayOf("mail", EmailErrors.INCORRECT_FORMAT),
                arrayOf("mail@", EmailErrors.INCORRECT_FORMAT),
                arrayOf("mail@mail", EmailErrors.INCORRECT_FORMAT),
                arrayOf("mail@mail.", EmailErrors.INCORRECT_FORMAT)
            )
        }
    }

    private lateinit var case: ValidateEmailCase

    @Before
    fun initialize() {
        case = ValidateEmailCaseImpl()
    }

    @Test
    fun validate() {
        assert(case.validate(email) == error)
    }
}