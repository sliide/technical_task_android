package com.android_test_maverick.local.repository

import com.android_test_maverick.UserModel
import com.android_test_maverick.local.AccessDao
import javax.inject.Inject

class LocalRepository @Inject constructor(private val yourDAO: AccessDao){

    suspend fun getAllItemsInDB() = yourDAO.getAllItems()

    suspend fun insertUserListInDB(list: List<UserModel>) = yourDAO.insertAll(list)

    suspend fun insertSingleUserInDB(user: UserModel) = yourDAO.insert(user)
}