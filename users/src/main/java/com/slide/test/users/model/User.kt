package com.slide.test.users.model

import com.slide.test.core.TimeFormatter
import com.slide.test.usecase.users.model.Gender
import com.slide.test.usecase.users.model.User
import com.slide.test.usecase.users.model.UserStatus

/**
 * Created by Stefan Halus on 19 May 2022
 */
data class UserUI(
    val id: Long,
    val name: String,
    val email: String,
    val gender: Gender,
    val status: UserStatus,
    val creationTime: String
)

fun User.toUI(timeFormatter: TimeFormatter): UserUI {
    return UserUI(
        id = id,
        name = name,
        email = email,
        gender = gender,
        status = status,
        creationTime = timeFormatter.formatDuration(creationTime)
    )
}