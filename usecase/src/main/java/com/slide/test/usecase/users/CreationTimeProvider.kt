package com.slide.test.usecase.users

import javax.inject.Inject

interface CreationTimeProvider {

    fun getAppStartTime(): Long

    fun getCurrentTime(): Long
}

class CreationTimeProviderImplementation @Inject constructor() : CreationTimeProvider {

    private val startTime = System.currentTimeMillis()

    override fun getAppStartTime(): Long {
        return startTime
    }

    override fun getCurrentTime(): Long {
        return System.currentTimeMillis()
    }

}
