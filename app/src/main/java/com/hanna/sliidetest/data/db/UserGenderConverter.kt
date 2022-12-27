package com.hanna.sliidetest.data.db

import androidx.room.TypeConverter
import com.hanna.sliidetest.models.UserGender

class UserGenderConverter {

    @TypeConverter
    fun genderToString(gender: UserGender): String = gender.gender

    @TypeConverter
    fun stringToOrderGender(value: String?): UserGender =
        UserGender.values().firstOrNull { gender ->
            gender.gender == value
        } ?: UserGender.UNDEFINED
}