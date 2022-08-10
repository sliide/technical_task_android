package com.sliide.demoapp.ui.adduser

import com.sliide.demoapp.model.Gender
import com.sliide.demoapp.model.Status
import com.sliide.demoapp.utils.redux.Action

/**
 * These are all of the possible actions that can be triggered from the add user screen.
 */
sealed class AddUserAction : Action {

    data class NameChanged(val newName: String) : AddUserAction()

    data class EmailChanged(val newEmail: String) : AddUserAction()

    data class GenderChanged(val gender: Gender) : AddUserAction()

    data class StatusChanged(val status: Status) : AddUserAction()

    object SaveButtonClicked : AddUserAction()

    object InitAction : AddUserAction()

}