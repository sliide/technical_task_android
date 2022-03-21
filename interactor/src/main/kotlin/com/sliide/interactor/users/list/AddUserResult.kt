package com.sliide.interactor.users.list

sealed class AddUserResult {
    data class Created(val user: UserItem) : AddUserResult()
    object FieldsError : AddUserResult()
    data class UnknownError(val throwable: Throwable) : AddUserResult()
}