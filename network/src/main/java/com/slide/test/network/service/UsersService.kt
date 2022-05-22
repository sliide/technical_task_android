package com.slide.test.network.service

import com.slide.test.network.CustomHeaders.NO_AUTH
import com.slide.test.network.model.PageDto
import com.slide.test.network.model.UserDto
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

/**
 * Created by Stefan Halus on 18 May 2022
 */

interface UsersService {

    @Headers("$NO_AUTH:true")
    @GET("public-api/users")
    fun fetchUsers(@Query("page") page: Long?): Single<PageDto<UserDto>>

    @DELETE("public-api/users/{userId}")
    fun deleteUser(@Path("userId") userId: Long): Completable
}