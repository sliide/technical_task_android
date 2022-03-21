package com.sliide.interactor.users.list

interface UserListInteractor {

    suspend fun addNewUser(name: String, email: String): AddUserResult

    suspend fun deleteUser(userId: Int): DeleteUserResult
}