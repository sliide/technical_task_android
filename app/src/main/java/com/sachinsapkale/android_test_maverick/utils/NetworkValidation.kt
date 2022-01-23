package com.sachinsapkale.android_test_maverick.utils

import android.content.Context
import android.net.ConnectivityManager


fun isNetworkAvailable(context: Context?): Boolean { // using old deprecated method as dont feel writing code for < Android M and other is not necessary now.
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
}
