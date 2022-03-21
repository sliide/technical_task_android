package com.sliide.di.users.create

import com.sliide.domain.users.ValidateEmailCase
import com.sliide.domain.users.ValidateEmailCaseImpl
import com.sliide.domain.users.ValidateNameCase
import com.sliide.domain.users.ValidateNameCaseImpl
import com.sliide.domain.users.create.CreateUserInteractorImpl
import com.sliide.interactor.users.create.CreateUserInteractor
import dagger.Binds
import dagger.Module

@Module
interface CreateUserModule {

    @Binds
    fun bindInteractor(interactor: CreateUserInteractorImpl): CreateUserInteractor

    @Binds
    fun bindValidateNameCase(case: ValidateNameCaseImpl): ValidateNameCase

    @Binds
    fun bindValidateEmailCase(case: ValidateEmailCaseImpl): ValidateEmailCase
}