package com.hanna.sliidetest.di

import com.hanna.sliidetest.data.repositories.repositories.UsersRepository
import com.hanna.sliidetest.domain.usecases.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
 object UseCaseModule {

    @Provides
    fun provideGetUsersUseCase(repository: UsersRepository): GetUsersUseCase = GetUsersUseCase(repository)
}