package com.sliide.interactor.users.list

interface UserListInteractor {

    suspend fun addNewUser(name: String, email: String): AddResult

    suspend fun deleteUser(userId: Int): DeleteResult
}