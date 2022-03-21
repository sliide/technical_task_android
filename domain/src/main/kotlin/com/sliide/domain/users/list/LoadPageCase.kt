package com.sliide.domain.users.list

import com.sliide.interactor.users.list.LoadPageResult

interface LoadPageCase {

    suspend fun load(page: Int): LoadPageResult
}