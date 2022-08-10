package com.sliide.demoapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sliide.demoapp.model.Gender
import com.sliide.demoapp.model.Status

@Entity(tableName = "Users")
data class UserCacheEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name ="id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "gender")
    val gender: Gender,

    @ColumnInfo(name = "status")
    val status: Status,

    @ColumnInfo(name = "timeStamp")
    val timeStamp: Long

)