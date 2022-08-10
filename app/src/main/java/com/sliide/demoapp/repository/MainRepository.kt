package com.sliide.demoapp.repository

import com.sliide.demoapp.model.User
import com.sliide.demoapp.utils.response.DataState

/**
 *  MainRepository interface responsible for defining repository capabilities
 */
interface MainRepository {

    suspend fun getUsers(): DataState<List<User>>

    suspend fun addUser(user: User): DataState<User>

    suspend fun deleteUser(userId: Int): DataState<Int>
}