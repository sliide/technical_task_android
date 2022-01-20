package com.android_test_maverick.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android_test_maverick.UserModel

@Database(entities = [UserModel::class], version = 1, exportSchema =false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accessDao(): AccessDao
}