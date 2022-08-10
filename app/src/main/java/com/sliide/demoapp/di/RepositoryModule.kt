package com.sliide.demoapp.di

import com.sliide.demoapp.repository.MainRepository
import com.sliide.demoapp.repository.MainRepositoryImpl
import com.sliide.demoapp.retrofit.UserNetworkMapper
import com.sliide.demoapp.retrofit.UserService
import com.sliide.demoapp.room.UserCacheMapper
import com.sliide.demoapp.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *  Module responsible with providing [MainRepository] instance with an Application Scope.
 */
@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        userService: UserService,
        userDao: UserDao,
        networkMapper: UserNetworkMapper,
        cacheMapper: UserCacheMapper
    ): MainRepository {
        return MainRepositoryImpl(userService, userDao, networkMapper, cacheMapper)
    }


}