package com.sliide.domain.users.list

import com.sliide.interactor.users.list.AddUserResult
import com.sliide.interactor.users.list.DeleteUserResult
import com.sliide.interactor.users.list.UserListInteractor
import javax.inject.Inject

class UserListInteractorImpl @Inject constructor(): UserListInteractor {
    override suspend fun addNewUser(name: String, email: String): AddUserResult {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(userId: Int): DeleteUserResult {
        TODO("Not yet implemented")
    }
}