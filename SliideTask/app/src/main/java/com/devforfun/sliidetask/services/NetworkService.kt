package com.devforfun.sliidetask.services

/**
 * Class through which network or Internet availability is queried
 */
interface NetworkService {
    /**
     * Returns [true], if a network connection is available (WiFi, Data, etc.). A value of [true] does not guarantee
     * that an Internet connection is also present. Use [isInternetConnectionAvailable] to check for the availability of
     * an Internet connection
     */
    fun isNetworkAvailable(): Boolean

    /**
     * Returns [true], if an Internet connection is available
     */
    fun isInternetConnectionAvailable(): Boolean
}

