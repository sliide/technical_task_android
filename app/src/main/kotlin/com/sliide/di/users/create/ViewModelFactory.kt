package com.sliide.di.users.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sliide.interactor.users.create.CreateUserInteractor
import com.sliide.presentation.users.create.CreateUserViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val interactor: CreateUserInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == CreateUserViewModel::class.java)
        return CreateUserViewModel(interactor) as T
    }
}