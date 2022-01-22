package com.android_test_maverick.remote.repository

import com.android_test_maverick.UserModel
import com.android_test_maverick.remote.RetrofitService
import javax.inject.Inject

class MainRepository @Inject constructor(private val retrofitService: RetrofitService) {

    suspend fun getUserListFromLastPage(pageNumber : Int) = retrofitService.getUserListFromLastPage(pageNumber)

    suspend fun createNewUser(token : String,user: UserModel) = retrofitService.createNewUser(token,user)

    suspend fun deleteUser(userId: Int,token : String) = retrofitService.deleteUser(userId,token)

}