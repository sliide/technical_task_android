package com.sliide.domain.users.list

import com.sliide.interactor.users.list.LoadPageResult
import com.sliide.interactor.users.list.PagesInteractor
import javax.inject.Inject

class PagesInteractorImpl @Inject constructor(
    private val loadPage: LoadPageCase
) : PagesInteractor {

    override suspend fun users(page: Int): LoadPageResult {
        return loadPage.load(page)
    }
}