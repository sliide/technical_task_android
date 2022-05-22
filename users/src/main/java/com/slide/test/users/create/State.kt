package com.slide.test.users.create

import com.slide.test.core_ui.mvi.BaseState
import com.slide.test.usecase.users.model.Gender

/**
 * Created by Stefan Halus on 19 May 2022
 */
data class State(
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userCreateSuccess:Boolean? = null,
    val createdUserName: String? = null,
    val userNameInput: String? = null,
    val emailInput: String? = null,
    val genderInput: Int = 0,
    val isEmailError: Boolean = false,
    val genderOptions: List<Gender> = emptyList()
) : BaseState
