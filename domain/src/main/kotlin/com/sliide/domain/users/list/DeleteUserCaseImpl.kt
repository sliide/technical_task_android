package com.sliide.domain.users.list

import com.sliide.boundary.users.UsersRepo
import com.sliide.interactor.users.list.DeleteResult
import javax.inject.Inject

class DeleteUserCaseImpl @Inject constructor(private val usersRepo: UsersRepo) : DeleteUserCase {

    override suspend fun deleteUser(userId: Int): DeleteResult {
        return try {
            usersRepo.delete(userId)
            DeleteResult.Deleted
        } catch (ex: Exception) {
            DeleteResult.UnknownError(ex)
        }
    }
}