package com.sachin_sapkale_android_challenge.di

import android.content.Context
import com.android_test_maverick.local.AppDatabase
import com.android_test_maverick.local.source.LocalDataSource
import com.android_test_maverick.remote.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApi() = RetrofitService.getInstance()

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext app: Context) =  LocalDataSource.getDatabase(app)

    @Singleton
    @Provides
    fun provideDao(db: AppDatabase) = db.accessDao()
}