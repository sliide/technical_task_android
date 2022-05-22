package com.slide.test.repository.model

import com.slide.test.network.model.CreateUserRequestDto


/**
 * Created by Stefan Halus on 22 May 2022
 */
data class CreateUserRequestModel(
    val name: String,
    val email: String,
    val gender: GenderModel,
    val statusModel: UserStatusModel
)

fun CreateUserRequestModel.toDto() : CreateUserRequestDto {
    return CreateUserRequestDto(
        name = name,
        email = email,
        gender = gender.key,
        status = statusModel.key
    )
}
