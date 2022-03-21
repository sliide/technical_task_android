package com.sliide.domain.users.create

import com.sliide.interactor.users.create.NameErrors
import javax.inject.Inject

class ValidateNameCaseImpl @Inject constructor() : ValidateNameCase {

    override fun validate(name: String): NameErrors {
        val cleaned = name.trim()

        return if (cleaned.isEmpty()) NameErrors.NAME_REQUIRED else NameErrors.NONE
    }
}