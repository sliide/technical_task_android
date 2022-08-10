package com.sliide.demoapp.ui.main

import com.sliide.demoapp.utils.redux.Reducer

/**
 * This reducer is responsible for handling any [UsersAction], and using that to create
 * a new [UsersViewState].
 */
class UsersReducer : Reducer<UsersViewState, UsersAction> {

    override fun reduce(currentState: UsersViewState, action: UsersAction): UsersViewState {
        return when (action) {
            is UsersAction.FetchUsersStarted -> {
                stateAfterFetchStarted(currentState)
            }
            is UsersAction.FetchUsersCompleted -> {
                stateAfterFetchCompleted(currentState, action)
            }
            is UsersAction.DeleteUserCompleted -> {
                stateAfterDeleteCompleted(currentState, action)
            }
            is UsersAction.DeleteUserStarted -> {
                stateAfterDeleteStarted(currentState)
            }
            is UsersAction.FetchUsersFailed -> {
                stateAfterFailedUserFetch(currentState, action)
            }
            is UsersAction.DeleteUserFailed -> {
                stateAfterFailUserDelete(currentState, action)
            }
            is UsersAction.AddUserCompleted -> {
                stateAfterUserAdded(currentState, action)
            }
            is UsersAction.AddUserStared -> {
                stateAfterAddStarted(currentState)
            }
            is UsersAction.AddUserUserFailed -> {
                stateAfterFailAddUser(currentState, action)
            }
        }
    }

    private fun stateAfterUserAdded(
        currentState: UsersViewState,
        action: UsersAction.AddUserCompleted
    ) : UsersViewState {

        val mutableList = currentState.usersList.toMutableList()
        mutableList.add(0, action.user)

        return currentState.copy(
            showProgressBar = false,
            usersList = mutableList.toList()
        )
    }

    private fun stateAfterFailUserDelete(
        currentState: UsersViewState,
        action: UsersAction.DeleteUserFailed
    ) = currentState.copy(
        showProgressBar = false,
        error = action.error
    )

    private fun stateAfterFailedUserFetch(
        currentState: UsersViewState,
        action: UsersAction.FetchUsersFailed
    ) = currentState.copy(
        showProgressBar = false,
        usersList = emptyList(),
        error = action.error
    )

    private fun stateAfterDeleteCompleted(
        currentState: UsersViewState,
        action: UsersAction.DeleteUserCompleted
    ) = currentState.copy(
        showProgressBar = false,
        usersList = currentState.usersList.filter { it.id != action.userId }
    )

    private fun stateAfterFailAddUser(
        currentState: UsersViewState,
        action: UsersAction.AddUserUserFailed
    ) = currentState.copy(
        showProgressBar = false,
        error = action.error
    )

    private fun stateAfterFetchCompleted(
        currentState: UsersViewState,
        action: UsersAction.FetchUsersCompleted
    ) = currentState.copy(
        showProgressBar = false,
        usersList = action.users
    )

    private fun stateAfterAddStarted(currentState: UsersViewState) =
        startProgress(currentState)

    private fun stateAfterFetchStarted(currentState: UsersViewState) =
        startProgress(currentState)

    private fun stateAfterDeleteStarted(currentState: UsersViewState) =
        startProgress(currentState)

    private fun startProgress(currentState: UsersViewState) = if (currentState.showProgressBar) {
        currentState
    } else {
        currentState.copy(
            showProgressBar = true,
            error = null
        )
    }
}