package com.sliide.interactor.users.list

sealed class DeleteResult {
    object Deleted : DeleteResult()
    data class UnknownError(val throwable: Throwable) : DeleteResult()
}