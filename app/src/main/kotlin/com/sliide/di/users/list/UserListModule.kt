package com.sliide.di.users.list

import com.sliide.boundary.users.UsersRepo
import com.sliide.data.users.UsersRepoImpl
import com.sliide.domain.users.AddUserCase
import com.sliide.domain.users.AddUserCaseImpl
import com.sliide.domain.users.DeleteUserCase
import com.sliide.domain.users.DeleteUserCaseImpl
import com.sliide.domain.users.list.UserListInteractorImpl
import com.sliide.interactor.users.list.UserListInteractor
import dagger.Binds
import dagger.Module

@Module
interface UserListModule {

    @Binds
    fun bindInteractor(interactor: UserListInteractorImpl): UserListInteractor

    @Binds
    fun bindAddUserCase(case: AddUserCaseImpl): AddUserCase

    @Binds
    fun bindDeleteUserCase(case: DeleteUserCaseImpl): DeleteUserCase

    @Binds
    fun bindUserRepo(repoImpl: UsersRepoImpl): UsersRepo
}