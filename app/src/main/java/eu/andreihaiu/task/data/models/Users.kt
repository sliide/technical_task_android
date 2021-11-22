package eu.andreihaiu.task.data.models

import com.squareup.moshi.Json

data class Users(
    @Json(name = "id") val id: Long? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "email") val email: String? = null,
    @Json(name = "gender") val gender: String? = null,
    @Json(name = "status") val status: String? = null
)

data class UsersResponse(
    @Json(name = "meta") val meta: Meta? = null,
    @Json(name = "data") val data: List<Users>? = null
)

data class AddUserResponse(
    @Json(name = "data") val data: Users
)

data class Meta(
    @Json(name = "pagination") val pagination: Pagination? = null
)

data class Pagination(
    @Json(name = "total") val total: Int? = null,
    @Json(name = "pages") val pages: Int? = null,
    @Json(name = "page") val page: Int? = null,
    @Json(name = "limit") val limit: Int? = null
)

data class DeleteUserResponse(
    @Json(name = "code") val code: Int = 0,
    @Json(name = "message") val message: String? = null,
    @Json(name = "url") val url: String? = null
)