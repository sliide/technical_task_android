package com.sliide.data.users

object UsersDtoFactory {

    fun createUsers(startIndex: Int, count: Int): List<UserDto> {
        return (0 until count).map { index ->
            val id = startIndex + index + 1
            createUser(
                id = id,
                name = "name$id",
                email = "email$id@email.com"
            )
        }
    }

    fun createUser(id: Int, name: String, email: String): UserDto {
        return UserDto(id = id, name = name, email = email, gender = "male", status = "active")
    }
}