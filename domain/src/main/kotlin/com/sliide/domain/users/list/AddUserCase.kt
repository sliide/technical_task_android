package com.sliide.domain.users

import com.sliide.interactor.users.AddUserResult

interface AddUserCase {

    suspend fun addUser(name: String, email: String): AddUserResult
}