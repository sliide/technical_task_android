package com.slide.test.core

import java.util.concurrent.TimeUnit

/**
 * Created by Stefan Halus on 19 May 2022
 */
interface TimeFormatter {

    fun formatDuration(duration: Long): String
}

class TimeFormatterImplementation : TimeFormatter {

    override fun formatDuration(duration: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(duration)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
