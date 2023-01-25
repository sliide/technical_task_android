package com.sliide.presentation.users.list

import androidx.paging.PagingSource
import com.sliide.interactor.users.list.UserItem

interface PagingSourceFactory {

    fun create(): PagingSource<Int, UserItem>
}