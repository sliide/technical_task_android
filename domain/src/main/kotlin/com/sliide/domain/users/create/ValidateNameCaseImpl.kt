package com.sliide.domain.users

import com.sliide.interactor.users.NameErrors
import javax.inject.Inject

class ValidateNameCaseImpl @Inject constructor() : ValidateNameCase {

    override fun validate(name: String): NameErrors {
        val cleaned = name.trim()

        return if (cleaned.isEmpty()) NameErrors.NAME_REQUIRED else NameErrors.NONE
    }
}