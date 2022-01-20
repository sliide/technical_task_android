package com.android_test_maverick

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itemsTable")
data class UserModel(@PrimaryKey val id: Int,
                 @ColumnInfo(name = "name",defaultValue = "") val name: String,
                 @ColumnInfo(name = "email",defaultValue = "") val email: String,
                 @ColumnInfo(name = "gender",defaultValue = "") val gender: String,
                 @ColumnInfo(name = "status",defaultValue = "") val status: String)