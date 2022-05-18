package com.hanna.sliidetest.data.mappers

import com.hanna.sliidetest.data.dto.UserDto
import com.hanna.sliidetest.models.User
import java.util.*

object UserMapper {

    fun map(userDto: UserDto): User {
        return User(
            id = userDto.id,
            name = userDto.name,
            email = userDto.email,
            creationTime = Date()
        )
    }
}