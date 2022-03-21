package com.sliide.interactor.users.list

sealed class AddUserResult {
    data class Created(val user: UserItem) : AddUserResult()
    object FieldsError : AddUserResult()
    object UnknownError : AddUserResult()
}