package com.sliide.interactor.users.list

interface PagesInteractor {

    suspend fun users(page: Int): List<UserItem>
}