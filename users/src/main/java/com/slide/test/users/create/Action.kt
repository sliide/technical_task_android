package com.slide.test.users.create

import com.slide.test.core_ui.mvi.BaseAction

/**
 * Created by Stefan Halus on 19 May 2022
 */
sealed class Action : BaseAction {

    data class UserFormUpdated(val userName: String?= null, val email: String?= null, val selectedGenderIndex: Int?= null) : Action()

    data class UserCreateRequest(val userName: String, val email: String, val selectedGenderIndex: Int) : Action()

    object ErrorAcknowledge : Action()

}
