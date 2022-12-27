package com.hanna.sliidetest.presenter

import androidx.lifecycle.*
import com.hanna.sliidetest.data.network.Resource
import com.hanna.sliidetest.domain.usecases.AddUserUseCase
import com.hanna.sliidetest.domain.usecases.DeleteUserUseCase
import com.hanna.sliidetest.domain.usecases.GetPageCountUseCase
import com.hanna.sliidetest.domain.usecases.GetUsersUseCase
import com.hanna.sliidetest.models.User
import com.hanna.sliidetest.models.UserGender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val getPageCountUseCase: GetPageCountUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<List<User>>>(Resource.loading(emptyList()))
    val usersData: StateFlow<Resource<List<User>>> = _uiState

    private val _addUserState = MutableLiveData<Resource<User>>(Resource.success(null))
    val addUserState: LiveData<Resource<User>> = Transformations.map(_addUserState){
        return@map it
    }
    private val _deleteUserState = MutableLiveData<Resource<Unit>>(Resource.success(null))
    val deleteUserState: LiveData<Resource<Unit>> = Transformations.map(_deleteUserState){
        return@map it
    }

    init {
        viewModelScope.launch {
            val lastPage = getPageCountUseCase()
            getUsersUseCase(lastPage).collect {
                _uiState.value = it
            }
        }
    }

    fun addUser(name: String, email: String, gender: String) {
        val user = User(
            name = name,
            email = email,
            gender = UserGender.values().firstOrNull { it.gender == gender } ?: UserGender.UNDEFINED
        )
        addUser(user)
    }

    private fun addUser(user: User) {
        viewModelScope.launch {
            addUserUseCase(user).collect {
                _addUserState.postValue(it)
            }
        }
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch {
            deleteUserUseCase(userId).collect {
                _deleteUserState.postValue(it)
            }
        }
    }
}

class UsersViewModelFactory @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val getPageCountUseCase: GetPageCountUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UsersViewModel(getUsersUseCase, getPageCountUseCase, addUserUseCase, deleteUserUseCase) as T
    }
}