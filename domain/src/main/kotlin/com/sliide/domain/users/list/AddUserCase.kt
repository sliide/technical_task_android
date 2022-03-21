package com.sliide.domain.users.list

import com.sliide.interactor.users.list.AddResult

interface AddUserCase {

    suspend fun addUser(name: String, email: String): AddResult
}