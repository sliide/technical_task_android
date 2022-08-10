package com.sliide.demoapp.ui.main

import com.sliide.demoapp.model.User
import com.sliide.demoapp.utils.redux.State

/**
 * An implementation of [State] that describes the configuration of the users list screen.
 */
data class UsersViewState(
    val showProgressBar: Boolean = false,
    val usersList: List<User> = emptyList(),
    val error: String? = null,
) : State