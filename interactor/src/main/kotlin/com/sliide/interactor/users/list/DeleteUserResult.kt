package com.sliide.interactor.users.list

sealed class DeleteUserResult {
    object Deleted : DeleteUserResult()
    data class UnknownError(val throwable: Throwable) : DeleteUserResult()
}