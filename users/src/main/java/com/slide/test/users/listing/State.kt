package com.slide.test.users.listing

import com.slide.test.core_ui.mvi.BaseState
import com.slide.test.users.model.UserUI

/**
 * Created by Stefan Halus on 19 May 2022
 */
data class State(
    val userList: List<UserUI> = listOf(),
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isEmpty: Boolean = false,
    val userToDelete: UserUI? = null
) : BaseState
