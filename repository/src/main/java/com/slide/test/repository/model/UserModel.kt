package com.slide.test.repository.model

import com.slide.test.network.model.UserDto

/**
 * Created by Stefan Halus on 19 May 2022
 */
data class UserModel(
    val id: Long,
    val name: String,
    val email: String,
    val gender: GenderModel,
    val status: UserStatusModel
)

fun UserDto.toModel(): UserModel {
    return UserModel(
        id = id,
        name = name,
        email = email,
        gender = GenderModel.fromKey(gender),
        status = UserStatusModel.fromKey(status)
    )
}
