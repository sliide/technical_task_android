package com.devforfun.sliidetask.services.impl


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.devforfun.sliidetask.services.NetworkService
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import timber.log.Timber

class NetworkServiceImpl(context: Context) : NetworkService {
    private val connectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun isNetworkAvailable(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isNetworkAvailableApi23()
        } else {
            isNetworkAvailableCompat()
        }
    }

    private fun isNetworkAvailableCompat(): Boolean {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo ?: return false

        return activeNetworkInfo.isConnected &&
                activeNetworkInfo.type == ConnectivityManager.TYPE_VPN ||
                activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI ||
                activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkAvailableApi23(): Boolean {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(
            connectivityManager.activeNetwork
        ) ?: return false

        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }

    override fun isInternetConnectionAvailable(): Boolean {
        return isNetworkAvailable() && isInternetConnectionAvailableInternal()
    }

    private fun isInternetConnectionAvailableInternal(): Boolean {
        return testUrls.any { testUrl ->
            try {
                val urlConnection = URL(testUrl).openConnection() as HttpURLConnection

                with(urlConnection.apply { connectTimeout = PING_TIMEOUT }) {
                    connect()
                    disconnect()
                }
                true
            } catch (e: IOException) {
                Timber.d(e, "Failed to check Internet access")
                false
            }
        }
    }

    companion object {
        private const val PING_TIMEOUT = 3000 // ms

        private val testUrls = arrayOf("https://google.com", "https://live.com")
    }
}
