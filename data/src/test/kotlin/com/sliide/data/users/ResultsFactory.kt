package com.sliide.data.users

import com.sliide.data.retrofit.HttpException
import com.sliide.data.retrofit.Result

object ResultsFactory {

    fun successUsers(page: Int, pageSize: Int): Result<List<UserDto>> {
        val users = UsersDtoFactory.createUsers((page - 1) * pageSize, pageSize)
        return Result.Success.HttpResponse(
            value = users,
            statusCode = 200,
            statusMessage = "OK",
            url = "https://url.com"
        )
    }

    fun failureUsers(): Result<List<UserDto>> {
        return Result.Failure.HttpError(HttpException(statusCode = 500))
    }
}