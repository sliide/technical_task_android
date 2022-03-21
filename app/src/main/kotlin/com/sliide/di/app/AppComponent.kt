package com.sliide.di.app

import com.sliide.di.network.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, CoroutinesModule::class])
interface AppComponent : AppProvider