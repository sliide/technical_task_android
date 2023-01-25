package com.sliide.domain.users.list

import com.sliide.boundary.users.UsersRepo
import com.sliide.interactor.users.list.LoadPageResult
import javax.inject.Inject

class LoadPageCaseImpl @Inject constructor(
    private val usersRepo: UsersRepo
) : LoadPageCase {

    override suspend fun load(page: Int): LoadPageResult {
        return try {
            val users = usersRepo.users(page).map { user -> user.toUserItem() }
            LoadPageResult.Loaded(users)
        } catch (ex: Exception) {
            LoadPageResult.UnknownError(ex)
        }
    }
}