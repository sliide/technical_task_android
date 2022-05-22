package com.slide.test.repository.di

import com.slide.test.repository.UsersRepository
import com.slide.test.repository.UsersRepositoryImplementation
import com.slide.test.repository.exceptions.UsersApiExceptionHandler
import com.slide.test.repository.exceptions.UsersApiExceptionHandlerImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Stefan Halus on 18 May 2022
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindsUsersRepositoryImplementation(
        usersRepositoryImplementation: UsersRepositoryImplementation
    ): UsersRepository

    @Binds
    internal abstract fun bindsUsersApiExceptionHandlerImplementation(
        usersRepositoryImplementation: UsersApiExceptionHandlerImplementation
    ): UsersApiExceptionHandler
}