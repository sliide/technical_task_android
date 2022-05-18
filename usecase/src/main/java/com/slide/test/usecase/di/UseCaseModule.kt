package com.slide.test.usecase.di

import com.slide.test.usecase.GetUsersUseCase
import com.slide.test.usecase.GetUsersUseCaseImplementation
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
    abstract fun bindGetUsersUseCase(
        getUsersUseCase: GetUsersUseCaseImplementation
    ): GetUsersUseCase
}