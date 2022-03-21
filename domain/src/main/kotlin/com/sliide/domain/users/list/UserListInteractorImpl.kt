package com.sliide.domain.users.list

import com.sliide.domain.users.AddUserCase
import com.sliide.domain.users.DeleteUserCase
import com.sliide.interactor.users.list.AddUserResult
import com.sliide.interactor.users.list.DeleteUserResult
import com.sliide.interactor.users.list.UserListInteractor
import javax.inject.Inject

class UserListInteractorImpl @Inject constructor(
    private val addUser: AddUserCase,
    private val deleteUser: DeleteUserCase
) : UserListInteractor {

    override suspend fun addNewUser(name: String, email: String): AddUserResult {
        return addUser.addUser(name, email)
    }

    override suspend fun deleteUser(userId: Int): DeleteUserResult {
        return deleteUser.deleteUser(userId)
    }
}