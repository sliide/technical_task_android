package com.android_test_maverick.local.source

import android.content.Context
import androidx.room.Room
import com.android_test_maverick.local.AppDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    fun destroyInstance(){
        INSTANCE = null
        println("---instance released---")
    }
}