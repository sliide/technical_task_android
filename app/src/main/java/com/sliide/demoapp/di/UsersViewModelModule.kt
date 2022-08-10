package com.sliide.demoapp.di

import com.sliide.demoapp.ui.main.UsersReducer
import com.sliide.demoapp.ui.main.UsersViewState
import com.sliide.demoapp.utils.redux.Store
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/**
 *  Module responsible with providing [Store] instance for users interactions functionality
 *  with an Application Scope.
 */
@Module
@InstallIn(SingletonComponent::class)
class UsersViewModelModule {

    @Singleton
    @Provides
    @Named("UsersStore")
    fun provideUsersStore() =
        Store(
            initialState = UsersViewState(),
            reducer = UsersReducer()
        )
}