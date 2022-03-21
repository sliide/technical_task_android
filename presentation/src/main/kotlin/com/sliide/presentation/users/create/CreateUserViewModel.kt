package com.sliide.presentation.users.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sliide.interactor.users.create.CreateUserInteractor
import com.sliide.interactor.users.create.EmailErrors
import com.sliide.interactor.users.create.NameErrors
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateUserViewModel(
    private val interactor: CreateUserInteractor
) : ViewModel() {

    private val mutableName = MutableStateFlow("")
    internal val name: StateFlow<String> = mutableName

    private val mutableEmail = MutableStateFlow("")
    internal val email: StateFlow<String> = mutableEmail

    private val mutableNameError = MutableStateFlow(NameErrors.NONE)
    internal val nameError: StateFlow<NameErrors> = mutableNameError

    private val mutableEmailError = MutableStateFlow(EmailErrors.NONE)
    internal val emailError: StateFlow<EmailErrors> = mutableEmailError

    private val mutableCreate = MutableStateFlow(false)
    internal val create: StateFlow<Boolean> = mutableCreate

    internal fun onCancelClick() {
        mutableName.value = ""
        mutableNameError.value = NameErrors.NONE
        mutableEmail.value = ""
        mutableEmailError.value = EmailErrors.NONE
    }

    internal fun onAddUserClick() {
        val name = mutableName.value.trim()
        val email = mutableEmail.value.trim()

        viewModelScope.launch {
            val emailDeferred = async { interactor.validateEmail(email) }
            val nameDeferred = async { interactor.validateName(name) }

            mutableEmailError.value = emailDeferred.await()
            mutableNameError.value = nameDeferred.await()

            if (nameError.value == NameErrors.NONE && emailError.value == EmailErrors.NONE) {
                mutableCreate.value = false
            }
        }
    }

    internal fun onNameChanged(name: String) {
        mutableName.value = name
        mutableNameError.value = NameErrors.NONE
    }

    internal fun onEmailChanged(email: String) {
        mutableEmail.value = email
        mutableEmailError.value = EmailErrors.NONE
    }

    internal fun createConsumed() {
        mutableCreate.value = false
    }
}