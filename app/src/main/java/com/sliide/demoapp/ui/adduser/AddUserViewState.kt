package com.sliide.demoapp.ui.adduser

import com.sliide.demoapp.model.Gender
import com.sliide.demoapp.model.Status
import com.sliide.demoapp.model.User
import com.sliide.demoapp.utils.redux.State
/**
 * An implementation of [State] that describes the configuration of the add user screen.
 */
data class AddUserViewState(
    val name: String = "",
    val email: String = "",
    val gender: Gender = Gender.MALE,
    val status: Status = Status.ACTIVE,
    val emailError: String? = null,
    val nameError: String? = null,
    val user: User? = null
) : State