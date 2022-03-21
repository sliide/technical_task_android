package com.sliide.domain.users

import com.sliide.interactor.users.DeleteUserResult

interface DeleteUserCase {

    suspend fun deleteUser(userId: Int): DeleteUserResult
}