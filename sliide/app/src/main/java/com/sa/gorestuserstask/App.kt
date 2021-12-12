package com.sa.gorestuserstask

import android.app.Application
import com.sa.gorestuserstask.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this).build()
    }

}
