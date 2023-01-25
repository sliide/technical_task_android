package com.sliide.domain.users.list

import com.sliide.interactor.users.list.AddResult
import com.sliide.interactor.users.list.DeleteResult
import com.sliide.interactor.users.list.UserListInteractor
import javax.inject.Inject

class UserListInteractorImpl @Inject constructor(
    private val addUser: AddUserCase,
    private val deleteUser: DeleteUserCase
) : UserListInteractor {

    override suspend fun addNewUser(name: String, email: String): AddResult {
        return addUser.addUser(name, email)
    }

    override suspend fun deleteUser(userId: Int): DeleteResult {
        return deleteUser.deleteUser(userId)
    }
}