package com.sachin_sapkale_android_challenge.local.repository

import com.sachin_sapkale_android_challenge.SingleItemModel
import com.sachin_sapkale_android_challenge.local.AccessDao
import javax.inject.Inject

class LocalRepository @Inject constructor(private val yourDAO: AccessDao){

    suspend fun getAllItemsInDB() = yourDAO.getAllItems()

    suspend fun insertSingleItemInDB(list: List<SingleItemModel>) = yourDAO.insertAll(list)
}