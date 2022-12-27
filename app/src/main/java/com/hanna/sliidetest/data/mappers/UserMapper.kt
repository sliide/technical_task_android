package com.hanna.sliidetest.data.mappers

import com.hanna.sliidetest.data.dto.UserDto
import com.hanna.sliidetest.models.User
import com.hanna.sliidetest.models.UserGender
import java.util.*

object UserMapper {

    fun map(userDto: UserDto): User {
        return User(
            id = userDto.id?: 0,
            name = userDto.name,
            email = userDto.email,
            gender = UserGender.values().firstOrNull { it.gender == userDto.gender } ?: UserGender.UNDEFINED,
            creationTime = Date()
        )
    }
}