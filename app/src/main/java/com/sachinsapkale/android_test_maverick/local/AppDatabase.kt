package com.sachin_sapkale_android_challenge.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sachin_sapkale_android_challenge.SingleItemModel

@Database(entities = [SingleItemModel::class], version = 1, exportSchema =false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accessDao(): AccessDao
}