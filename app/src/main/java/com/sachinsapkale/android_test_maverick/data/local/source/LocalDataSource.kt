package com.android_test_maverick.local.source

import android.content.Context
import androidx.room.Room
import com.android_test_maverick.local.AppDatabase
import javax.inject.Inject

class LocalDataSource @Inject constructor() {

    companion object {
        var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context,AppDatabase::class.java, "androidlocaldb")
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}