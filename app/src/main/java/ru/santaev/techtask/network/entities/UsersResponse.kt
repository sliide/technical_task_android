package ru.santaev.techtask.network.entities

data class UsersResponse(
    val meta: ResponseMetaInfo,
    val data: List<UserEntity>
)

data class ResponseMetaInfo(
    val pagination: PaginationMetaInfo
)

data class PaginationMetaInfo(
    val total: Int,
    val pages: Int,
    val page: Int,
    val limit: Int
)

data class UserEntity(
    val id: Long,
    val name: String,
    val email: String
)