package com.sliide.domain.users.list

import com.sliide.interactor.users.list.AddUserResult

interface AddUserCase {

    suspend fun addUser(name: String, email: String): AddUserResult
}