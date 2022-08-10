package com.sliide.demoapp.ui.adduser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sliide.demoapp.model.Gender
import com.sliide.demoapp.model.Status
import com.sliide.demoapp.ui.main.UsersAction
import com.sliide.demoapp.ui.main.UsersViewModel
import com.sliide.demoapp.utils.redux.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

/**
 * The [AddUserViewModel] is responsible for controlling the UI logic of the login screen. It will
 * listen for actions and update the UI state accordingly and expose that so the View can update.
 *
 * Whenever a view action occurs, such as [nameChanged] or [emailChanged], proxy the
 * corresponding [AddUserAction] to our [store].
 */
@HiltViewModel
class AddUserViewModel
@Inject
constructor(
    @Named("AddUserStore") private val store: Store<AddUserViewState, AddUserAction>
) : ViewModel() {

    val viewState: StateFlow<AddUserViewState> = store.state

    fun nameChanged(newName: String) {
        val action =  AddUserAction.NameChanged(newName)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun emailChanged(email: String) {
        val action =  AddUserAction.EmailChanged(email)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun genderChanged(gender: Gender) {
        val action = AddUserAction.GenderChanged(gender)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun statusChanged(status: Status) {
        val action = AddUserAction.StatusChanged(status)

        viewModelScope.launch {
            store.dispatch(action)
        }
    }

    fun savedButtonClicked() {
        val action = AddUserAction.SaveButtonClicked

        viewModelScope.launch {
            store.dispatch(action)
        }
    }
}