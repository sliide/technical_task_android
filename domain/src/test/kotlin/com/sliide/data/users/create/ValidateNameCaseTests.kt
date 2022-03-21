package com.sliide.data.users.create

import com.sliide.domain.users.create.ValidateNameCase
import com.sliide.domain.users.create.ValidateNameCaseImpl
import com.sliide.interactor.users.create.NameErrors
import org.junit.Before
import org.junit.Test

class ValidateNameCaseTests {

    private lateinit var case: ValidateNameCase

    @Before
    fun initialize() {
        case = ValidateNameCaseImpl()
    }

    @Test
    fun `validate empty name EXPECT NAME_REQUIRED`() {
        assert(case.validate("") == NameErrors.NAME_REQUIRED)
    }

    @Test
    fun `validate not empty name EXPECT NONE`() {
        assert(case.validate("Konstanty Kalinowski") == NameErrors.NONE)
    }
}