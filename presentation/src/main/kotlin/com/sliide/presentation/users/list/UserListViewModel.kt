package com.sliide.presentation.users.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sliide.interactor.users.list.AddUserResult
import com.sliide.interactor.users.list.UserItem
import com.sliide.interactor.users.list.UserListInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserListViewModel @Inject constructor(
    private val interactor: UserListInteractor
) : ViewModel() {

    private val mutableDialog = MutableStateFlow<Dialogs>(Dialogs.None)
    internal val dialog: StateFlow<Dialogs> = mutableDialog

    private val mutableErrors = MutableStateFlow(UsersError.NONE)
    internal val errors: StateFlow<UsersError> = mutableErrors

    private val mutableItems = MutableStateFlow(emptyList<UserItem>())
    internal val items: StateFlow<List<UserItem>> = mutableItems

    fun addUser(name: String, email: String) {
        viewModelScope.launch {
            mutableDialog.value = Dialogs.None

            when (interactor.addNewUser(name, email)) {
                is AddUserResult.Created -> {
                    // TODO
                }
                AddUserResult.FieldsError -> mutableErrors.value = UsersError.ADD_USER_FIELD
                AddUserResult.UnknownError -> mutableErrors.value = UsersError.UNKNOWN
            }
        }
    }

    fun removeUser(userId: Int) {
        mutableDialog.value = Dialogs.None

        viewModelScope.launch {
            val result = interactor.deleteUser(userId)
            // TODO
        }
    }

    fun onDismissDialog() {
        mutableDialog.value = Dialogs.None
    }

    internal fun onItemLongClick(item: UserItem) {
        mutableDialog.value = Dialogs.ConfirmRemove(item.id)
    }

    internal fun onFabClick() {
        mutableDialog.value = Dialogs.CreateUser
    }
}