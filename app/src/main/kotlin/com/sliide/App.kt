package com.sliide

import android.app.Application
import android.os.StrictMode
import com.sliide.di.app.AppProvider
import com.sliide.di.app.DaggerAppComponent

class App : Application() {

    lateinit var appProvider: AppProvider

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) setupStrictMode()
        setupDi()
    }

    private fun setupStrictMode() {
        val threadPolicy = StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .build()
        StrictMode.setThreadPolicy(threadPolicy)

        val vmPolicy = StrictMode.VmPolicy.Builder()
            .detectAll()
            .build()
        StrictMode.setVmPolicy(vmPolicy)
    }

    private fun setupDi() {
        appProvider = DaggerAppComponent.builder()
            .build()
    }
}