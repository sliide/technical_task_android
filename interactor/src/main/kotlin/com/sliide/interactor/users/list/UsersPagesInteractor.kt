package com.sliide.interactor.users.list

interface UsersPagesInteractor {
    suspend fun users(page: Int): List<UserItem>
}