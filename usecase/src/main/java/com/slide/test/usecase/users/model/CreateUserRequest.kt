package com.slide.test.usecase.users.model

import com.slide.test.repository.model.CreateUserRequestModel
import com.slide.test.repository.model.UserStatusModel

/**
 * Created by Stefan Halus on 22 May 2022
 */
data class CreateUserRequest(
    val name: String,
    val email: String,
    val gender: Gender
)


fun CreateUserRequest.toModel(): CreateUserRequestModel {
    return CreateUserRequestModel(
        name,
        email,
        gender.toModel(),
        UserStatusModel.ACTIVE
    )
}