package com.sliide.di.app

import com.sliide.data.IoDispatcher
import com.sliide.di.network.NetworkProvider
import kotlinx.coroutines.CoroutineDispatcher

interface AppProvider : NetworkProvider {

    @IoDispatcher
    fun io(): CoroutineDispatcher
}