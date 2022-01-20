package com.sachin_sapkale_android_challenge

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itemsTable")
data class SingleItemModel(@PrimaryKey val id: Int,
                 @ColumnInfo(name = "user") val user: String,
                 @ColumnInfo(name = "previewURL",defaultValue = "") val previewUrl: String,
                 @ColumnInfo(name = "tags") val tags: String)