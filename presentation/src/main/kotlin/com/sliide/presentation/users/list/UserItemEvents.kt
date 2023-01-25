package com.sliide.presentation.users.list

import com.sliide.interactor.users.list.UserItem

sealed class UserItemEvents {
    data class Add(val item: UserItem) : UserItemEvents()
    data class Remove(val userId: Int) : UserItemEvents()
}