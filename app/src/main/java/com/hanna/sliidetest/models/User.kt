package com.hanna.sliidetest.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hanna.sliidetest.data.db.DateConverter
import com.hanna.sliidetest.data.db.UserGenderConverter
import java.util.*

@Entity
@TypeConverters(UserGenderConverter::class, DateConverter::class)
data class User(
    @PrimaryKey val id: Int = 0,
    val name: String,
    val email: String,
    val gender: UserGender,
    val creationTime: Date = Date()
)