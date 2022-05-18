package com.slide.test.network.service

import com.slide.test.network.UserDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

/**
 * Created by Stefan Halus on 18 May 2022
 */

interface UsersService {

    @GET("public/v2/users")
    fun fetchUsers() : Single<List<UserDto>>

}