package com.sliide.demoapp.ui.main

import com.sliide.demoapp.model.User
import com.sliide.demoapp.utils.redux.Action

/**
 * These are all of the possible actions that can be triggered from the users list screen.
 */
sealed class UsersAction : Action {

    object FetchUsersStarted : UsersAction()

    data class FetchUsersCompleted(val users: List<User>) : UsersAction()

    data class FetchUsersFailed(val error: String) : UsersAction()

    object AddUserStared : UsersAction()

    data class AddUserCompleted(val user: User) : UsersAction()

    data class AddUserUserFailed(val error: String) : UsersAction()

    object DeleteUserStarted : UsersAction()

    data class DeleteUserCompleted(val userId: Int) : UsersAction()

    data class DeleteUserFailed(val error: String) : UsersAction()

}