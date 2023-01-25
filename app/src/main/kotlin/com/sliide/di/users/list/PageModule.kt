package com.sliide.di.users.list

import com.sliide.domain.users.list.LoadPageCase
import com.sliide.domain.users.list.LoadPageCaseImpl
import com.sliide.domain.users.list.PagesInteractorImpl
import com.sliide.interactor.users.list.PagesInteractor
import com.sliide.presentation.users.list.PagingSourceFactory
import com.sliide.presentation.users.list.PagingSourceFactoryImpl
import dagger.Binds
import dagger.Module

@Module
interface PageModule {

    @Binds
    fun bindPagingSourceFactory(factory: PagingSourceFactoryImpl): PagingSourceFactory

    @Binds
    fun bindPagesInteractor(interactor: PagesInteractorImpl): PagesInteractor

    @Binds
    fun bindLoadPageCase(case: LoadPageCaseImpl): LoadPageCase
}