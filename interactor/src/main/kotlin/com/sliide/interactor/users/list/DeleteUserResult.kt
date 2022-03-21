package com.sliide.interactor.users.list

sealed class DeleteUserResult {
    object Deleted : DeleteUserResult()
    object UnknownError : DeleteUserResult()
}