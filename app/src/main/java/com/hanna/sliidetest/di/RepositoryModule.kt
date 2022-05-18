package com.hanna.sliidetest.di

import com.hanna.sliidetest.data.repositories.repositories.UsersRepository
import com.hanna.sliidetest.data.repositories.repositoriesImpl.UsersRepositoryImpl
import com.hanna.sliidetest.domain.usecases.GetUsersUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideUsersRepository(repositoryImpl: UsersRepositoryImpl): UsersRepository

}
