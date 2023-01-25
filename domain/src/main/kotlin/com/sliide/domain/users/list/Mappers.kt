package com.sliide.domain.users.list

import com.sliide.boundary.users.User
import com.sliide.interactor.users.list.UserItem

fun User.toUserItem(): UserItem = UserItem(id, name, email, "TODO")