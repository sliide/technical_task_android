package com.slide.test.usecase.users.model

import com.slide.test.repository.model.UserStatusModel

/**
 * Created by Stefan Halus on 19 May 2022
 */
enum class UserStatus {
    ACTIVE,
    INACTIVE,
    UNKNOWN
}


fun UserStatusModel.toUseCase(): UserStatus {
    return when (this) {
        UserStatusModel.ACTIVE -> UserStatus.ACTIVE
        UserStatusModel.INACTIVE -> UserStatus.INACTIVE
        UserStatusModel.UNKNOWN -> UserStatus.UNKNOWN
    }
}