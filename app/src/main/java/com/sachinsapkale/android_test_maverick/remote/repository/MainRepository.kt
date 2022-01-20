package com.sachin_sapkale_android_challenge.remote.repository

import com.sachin_sapkale_android_challenge.remote.RetrofitService
import javax.inject.Inject

class MainRepository @Inject constructor(private val retrofitService: RetrofitService) {

    suspend fun getSearchList() = retrofitService.getSearchList()

}