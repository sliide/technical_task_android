package com.hanna.sliidetest.data.repositories.repositories

import com.hanna.sliidetest.data.network.Resource
import com.hanna.sliidetest.models.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun getPageCount(): Int
    suspend fun getUsers(page: Int): Flow<Resource<List<User>>>
    suspend fun addUser(user: User): Flow<Resource<User>>
}