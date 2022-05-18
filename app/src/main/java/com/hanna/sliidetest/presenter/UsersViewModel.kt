package com.hanna.sliidetest.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hanna.sliidetest.data.network.Resource
import com.hanna.sliidetest.domain.usecases.GetMetadataUseCase
import com.hanna.sliidetest.domain.usecases.GetUsersUseCase
import com.hanna.sliidetest.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val getMetadataUseCase: GetMetadataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<List<User>>>(Resource.loading(emptyList()))
    val usersData: StateFlow<Resource<List<User>>> = _uiState

    init {
        viewModelScope.launch {
            val meta = getMetadataUseCase()
            val lastPage = meta.data?.pagination?.pages
            getUsersUseCase(lastPage ?: 1).collect {
                _uiState.value = it
            }
        }
    }
}

class UsersViewModelFactory @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val getMetadataUseCase: GetMetadataUseCase
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UsersViewModel(getUsersUseCase, getMetadataUseCase) as T
    }
}