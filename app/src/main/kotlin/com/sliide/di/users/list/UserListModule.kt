package com.sliide.di.users.list

import com.sliide.boundary.users.UsersRepo
import com.sliide.data.users.UsersRepoImpl
import com.sliide.domain.users.list.UserListInteractorImpl
import com.sliide.interactor.users.list.UserListInteractor
import dagger.Binds
import dagger.Module

@Module
interface UserListModule {

    @Binds
    fun bindUserRepo(repoImpl: UsersRepoImpl): UsersRepo

    @Binds
    fun bindInteractor(interactor: UserListInteractorImpl): UserListInteractor
}