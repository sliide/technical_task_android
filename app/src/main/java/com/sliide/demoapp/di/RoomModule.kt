package com.sliide.demoapp.di

import android.content.Context
import androidx.room.Room
import com.sliide.demoapp.model.User
import com.sliide.demoapp.room.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *  Module responsible with providing data-base capabilities for caching purpose
 *  with an Application Scope.
 */
@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Provides
    @Singleton
    fun provideUsersDb(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            UserDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUsersDao(database: UserDatabase) = database.usersDao()
}