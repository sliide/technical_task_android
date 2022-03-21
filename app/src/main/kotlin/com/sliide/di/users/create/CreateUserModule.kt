package com.sliide.di.users.create

import com.sliide.domain.users.create.ValidateEmailCase
import com.sliide.domain.users.create.ValidateEmailCaseImpl
import com.sliide.domain.users.create.ValidateNameCase
import com.sliide.domain.users.create.ValidateNameCaseImpl
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