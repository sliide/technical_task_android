package com.slide.test.usecase.di

import com.slide.test.usecase.users.*
import com.slide.test.usecase.users.DeleteUserUseCaseImplementation
import com.slide.test.usecase.users.GetLatestUsersUseCaseImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Stefan Halus on 18 May 2022
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    internal abstract fun bindGetUsersUseCase(
        getUsersUseCase: GetLatestUsersUseCaseImplementation
    ): GetLatestUsersUseCase


    @Binds
    internal abstract fun bindDeleteUserUseCase(
        getUsersUseCase: DeleteUserUseCaseImplementation
    ): DeleteUserUseCase

    @Binds
    internal abstract fun bindCreateUserUseCase(
        getUsersUseCase: CreateUserUseCaseImplementation
    ): CreateUserUseCase


    @Binds
    internal abstract fun bindCreationTimeProvider(
        getUsersUseCase: CreationTimeProviderImplementation
    ): CreationTimeProvider


}