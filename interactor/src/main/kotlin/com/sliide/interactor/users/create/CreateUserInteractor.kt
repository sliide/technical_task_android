package com.sliide.interactor.users.create

interface CreateUserInteractor {

    suspend fun validateName(name: String): NameErrors

    suspend fun validateEmail(email: String): EmailErrors
}