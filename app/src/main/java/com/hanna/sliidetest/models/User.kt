package com.hanna.sliidetest.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hanna.sliidetest.data.db.DateConverter
import java.util.*

@Entity
@TypeConverters(DateConverter::class)
data class User(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val creationTime: Date
)