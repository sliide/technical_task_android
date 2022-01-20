package com.android_test_maverick.local.repository

import com.android_test_maverick.SingleItemModel
import com.android_test_maverick.local.AccessDao
import javax.inject.Inject

class LocalRepository @Inject constructor(private val yourDAO: AccessDao){

    suspend fun getAllItemsInDB() = yourDAO.getAllItems()

    suspend fun insertSingleItemInDB(list: List<SingleItemModel>) = yourDAO.insertAll(list)
}