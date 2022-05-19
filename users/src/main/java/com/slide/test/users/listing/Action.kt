package com.slide.test.users.listing

import com.slide.test.core_ui.mvi.BaseAction
import com.slide.test.usecase.users.model.User
import com.slide.test.users.model.UserUI

/**
 * Created by Stefan Halus on 19 May 2022
 */
sealed class Action : BaseAction {

    object LoadUserList : Action()

    class UserDeleteAction(val user: UserUI) : Action()

    object AddUserButtonTapped : Action()

    class AddUser(email: String, name: String) : Action()

}
