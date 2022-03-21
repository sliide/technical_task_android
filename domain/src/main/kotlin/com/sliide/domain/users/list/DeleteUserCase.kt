package com.sliide.domain.users.list

import com.sliide.interactor.users.list.DeleteResult

interface DeleteUserCase {

    suspend fun deleteUser(userId: Int): DeleteResult
}