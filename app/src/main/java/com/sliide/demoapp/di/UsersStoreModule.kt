package com.sliide.demoapp.di

import com.sliide.demoapp.ui.adduser.AddUserReducer
import com.sliide.demoapp.ui.adduser.AddUserViewState
import com.sliide.demoapp.utils.redux.Store
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named

/**
 *  Module responsible with providing [Store] instance for adding user functionality
 *  with an ViewModel Scope.
 */
@Module
@InstallIn(ViewModelComponent::class)
class UsersStoreModule {

    @Provides
    @ViewModelScoped
    @Named("AddUserStore")
    fun provideAddUserStore() =
        Store(
            initialState = AddUserViewState(),
            reducer = AddUserReducer()
        )
}