package com.sliide.di.users.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sliide.interactor.users.list.UserListInteractor
import com.sliide.presentation.users.list.PagingSourceFactory
import com.sliide.presentation.users.list.UserListViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val interactor: UserListInteractor,
    private val pagingSourceFactory: PagingSourceFactory
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == UserListViewModel::class.java)
        return UserListViewModel(interactor, pagingSourceFactory) as T
    }
}