package com.slide.test.users.delete

import com.slide.test.core_ui.mvi.BaseState
import com.slide.test.users.model.UserUI

/**
 * Created by Stefan Halus on 19 May 2022
 */
data class State(
    val userName: String,
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userDeleteSuccess:Boolean? = null,
) : BaseState
