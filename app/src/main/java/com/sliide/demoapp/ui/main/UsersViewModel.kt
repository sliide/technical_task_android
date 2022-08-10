package com.sliide.demoapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sliide.demoapp.model.User
import com.sliide.demoapp.repository.MainRepository
import com.sliide.demoapp.utils.redux.Store
import com.sliide.demoapp.utils.response.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

/**
 * The [UsersViewModel] is responsible for controlling the UI logic of the login screen. It will
 * listen for actions and update the UI state accordingly and expose that so the View can update.
 *
 * Whenever a view action occurs, such as [getUsers] or [deleteUser], proxy the
 * corresponding [UsersAction] to our [store].
 */
@HiltViewModel
class UsersViewModel
@Inject
constructor(
    private val mainRepository: MainRepository,
    @Named("UsersStore") private val store: Store<UsersViewState, UsersAction>
) : ViewModel() {

    val viewState: StateFlow<UsersViewState> = store.state

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {

            val action = UsersAction.FetchUsersStarted
            store.dispatch(action)

            when (val response = mainRepository.getUsers()) {
                is DataState.Success -> store.dispatch(UsersAction.FetchUsersCompleted(response.data))
                is DataState.Error -> store.dispatch(UsersAction.FetchUsersFailed(response.error))
            }
        }
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            store.dispatch(UsersAction.AddUserStared)
            when (val response = mainRepository.addUser(user)) {
                is DataState.Success ->
                    store.dispatch(UsersAction.AddUserCompleted(response.data))
                is DataState.Error ->
                    store.dispatch(UsersAction.AddUserUserFailed(response.error))
            }
        }
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch {
            store.dispatch(UsersAction.DeleteUserStarted)
                when (val response = mainRepository.deleteUser(userId)) {
                    is DataState.Success ->
                        store.dispatch(UsersAction.DeleteUserCompleted(userId))
                    is DataState.Error ->
                        store.dispatch(UsersAction.DeleteUserFailed(response.error))
                }
        }
    }
}