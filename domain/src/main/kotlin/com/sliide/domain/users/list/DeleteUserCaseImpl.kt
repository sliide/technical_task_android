package com.sliide.domain.users.list

import com.sliide.boundary.users.UsersRepo
import com.sliide.interactor.users.list.DeleteUserResult
import javax.inject.Inject

class DeleteUserCaseImpl @Inject constructor(private val usersRepo: UsersRepo) : DeleteUserCase {

    override suspend fun deleteUser(userId: Int): DeleteUserResult {
        return try {
            usersRepo.delete(userId)
            DeleteUserResult.Deleted
        } catch (ex: Exception) {
            DeleteUserResult.UnknownError(ex)
        }
    }
}