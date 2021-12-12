package com.sa.gorestuserstask.data.remote

import com.google.gson.annotations.SerializedName


data class UserApiResponse(
    val meta: Meta = Meta(),
    @SerializedName("data")
    val users: List<UserApiModel> = listOf()
)

data class Meta(
    val pagination: Pagination = Pagination()
)

data class Pagination(
    val total: Int = 0,
    val pages: Int = 0,
    val page: Int = 0,
    val limit: Int = 0
)

data class UserApiModel(
    val id: Int = -1,
    val name: String = "",
    val email: String = "",
    val gender: String = "",
    val status: String = ""
)

data class UserApiRequest(
    val name: String,
    val email: String,
    val gender: String,
    val status: String
)

data class AddUserApiResponse(
    @SerializedName("data")
    val user: UserApiModel = UserApiModel()
)


