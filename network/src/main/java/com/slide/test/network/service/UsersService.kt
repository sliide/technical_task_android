package com.slide.test.network.service

import com.slide.test.network.model.CreateUserRequestDto
import com.slide.test.network.model.ListResponseDto
import com.slide.test.network.model.ResponseDto
import com.slide.test.network.model.UserDto
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

/**
 * Created by Stefan Halus on 18 May 2022
 */

interface UsersService {

    @GET("public-api/users")
    fun fetchUsers(@Query("page") page: Long?): Single<ListResponseDto<UserDto>>

    @DELETE("public-api/users/{userId}")
    fun deleteUser(@Path("userId") userId: Long): Completable

    @POST("public-api/users")
    fun createUser(@Body createRequestDto: CreateUserRequestDto): Single<ResponseDto<UserDto>>
}