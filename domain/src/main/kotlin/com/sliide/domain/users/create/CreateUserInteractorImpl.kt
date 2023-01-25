package com.sliide.domain.users.create

import com.sliide.interactor.users.create.CreateUserInteractor
import com.sliide.interactor.users.create.EmailErrors
import com.sliide.interactor.users.create.NameErrors
import javax.inject.Inject

class CreateUserInteractorImpl @Inject constructor(
    private val validateName: ValidateNameCase,
    private val validateEmailCase: ValidateEmailCase
) : CreateUserInteractor {

    override suspend fun validateName(name: String): NameErrors {
        return validateName.validate(name)
    }

    override suspend fun validateEmail(email: String): EmailErrors {
        return validateEmailCase.validate(email)
    }
}