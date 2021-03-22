package com.devforfun.sliidetask;

import android.app.Application
import com.devforfun.sliidetask.di.networkModule
import com.devforfun.sliidetask.di.repositoryModule
import com.devforfun.sliidetask.di.serviceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class SliideTaskApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@SliideTaskApplication)
            modules( listOf(networkModule, serviceModule, repositoryModule))
        }
    }
}