package com.android_test_maverick.remote.repository

import com.android_test_maverick.remote.RetrofitService
import javax.inject.Inject

class MainRepository @Inject constructor(private val retrofitService: RetrofitService) {

    suspend fun getSearchList(pageNumber : Int) = retrofitService.getSearchList(pageNumber)

    suspend fun createNewUser(token : String) = retrofitService.createNewUser(token)

    suspend fun deleteUser(token : String) = retrofitService.deleteUser(token)

}