package com.sliide.domain.users.list

import com.sliide.boundary.users.UsersRepo
import com.sliide.interactor.users.list.UserItem
import com.sliide.interactor.users.list.PagesInteractor
import javax.inject.Inject

class PagesInteractorImpl @Inject constructor(
    private val usersRepo: UsersRepo
) : PagesInteractor {

    override suspend fun users(page: Int): List<UserItem> {
        return usersRepo.users(page).map { user -> user.toUserItem() }
    }
}