package com.sliide.interactor.users.list

sealed class AddResult {
    data class Created(val user: UserItem) : AddResult()
    object FieldsError : AddResult()
    data class EmailAlreadyTaken(val email: String) : AddResult()
    data class UnknownError(val throwable: Throwable) : AddResult()
}