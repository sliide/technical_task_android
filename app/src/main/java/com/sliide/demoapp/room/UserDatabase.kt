package com.sliide.demoapp.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserCacheEntity::class ], version = 1)
abstract class UserDatabase: RoomDatabase() {

    abstract fun usersDao(): UserDao

    companion object{
        const val DATABASE_NAME: String = "users_db"
    }

}