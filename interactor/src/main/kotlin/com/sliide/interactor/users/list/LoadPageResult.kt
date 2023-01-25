package com.sliide.interactor.users.list

sealed class LoadPageResult {

    data class Loaded(val items: List<UserItem>) : LoadPageResult()

    data class UnknownError(val throwable: Throwable) : LoadPageResult()
}