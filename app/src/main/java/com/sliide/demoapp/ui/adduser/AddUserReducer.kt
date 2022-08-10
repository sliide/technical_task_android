package com.sliide.demoapp.ui.adduser

import com.sliide.demoapp.model.User
import com.sliide.demoapp.utils.redux.Reducer
import java.util.regex.Pattern

/**
 * This reducer is responsible for handling any [AddUserAction], and using that to create
 * a new [AddUserViewState].
 */
class AddUserReducer : Reducer<AddUserViewState, AddUserAction> {

    private val emailPattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    override fun reduce(currentState: AddUserViewState, action: AddUserAction): AddUserViewState {
        return when (action) {
            is AddUserAction.NameChanged -> {
                stateAfterNamedChanged(currentState, action)
            }
            is AddUserAction.EmailChanged -> {
                stateAfterEmailChanged(currentState, action)
            }
            is AddUserAction.GenderChanged -> {
                stateAfterGenderChanged(currentState, action)
            }
            is AddUserAction.StatusChanged -> {
                stateAfterStatusChanged(currentState, action)
            }
            is AddUserAction.SaveButtonClicked -> {
                stateAfterSaveSubmit(currentState)
            }
            else -> currentState.copy()
        }
    }

    private fun stateAfterSaveSubmit(currentState: AddUserViewState): AddUserViewState {
        if (currentState.name.length < 3) {
            return currentState.copy(
                nameError = "Name size should be bigger than 3",
            )
        }
        if (!isValidEmail(currentState.email)) {
            return currentState.copy(
                emailError = "Invalid email format"
            )
        }

        return currentState.copy(
            user = User(
                0,
                currentState.name,
                currentState.email,
                currentState.gender,
                currentState.status,
            )
        )
    }

    private fun stateAfterStatusChanged(
        currentState: AddUserViewState,
        action: AddUserAction.StatusChanged
    ) = currentState.copy(
        status = action.status
    )

    private fun stateAfterGenderChanged(
        currentState: AddUserViewState,
        action: AddUserAction.GenderChanged
    ) = currentState.copy(
        gender = action.gender
    )

    private fun stateAfterEmailChanged(
        currentState: AddUserViewState,
        action: AddUserAction.EmailChanged
    ) = currentState.copy(
        email = action.newEmail,
        emailError = ""
    )

    private fun stateAfterNamedChanged(
        currentState: AddUserViewState,
        action: AddUserAction.NameChanged
    ) = currentState.copy(
        name = action.newName,
        nameError = ""
    )

    private fun isValidEmail(email: String): Boolean {
        return emailPattern.matcher(email).matches()
    }
}