package com.hanna.sliidetest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hanna.sliidetest.models.User

@Database(entities = [User::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}