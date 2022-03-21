package com.sliide.domain.users.create

import com.sliide.interactor.users.create.CreateUserInteractor
import com.sliide.interactor.users.create.EmailErrors
import com.sliide.interactor.users.create.NameErrors
import javax.inject.Inject

class CreateUserInteractorImpl @Inject constructor() : CreateUserInteractor {

    override suspend fun validateName(name: String): NameErrors {
        TODO("Not yet implemented")
    }

    override suspend fun validateEmail(email: String): EmailErrors {
        TODO("Not yet implemented")
    }
}