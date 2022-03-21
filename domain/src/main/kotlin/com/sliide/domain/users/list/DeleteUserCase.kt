package com.sliide.domain.users.list

import com.sliide.interactor.users.list.DeleteUserResult

interface DeleteUserCase {

    suspend fun deleteUser(userId: Int): DeleteUserResult
}