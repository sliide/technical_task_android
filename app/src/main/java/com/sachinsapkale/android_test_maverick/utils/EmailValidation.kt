package com.what3words.lib

import android.util.Patterns

fun String.isEmailValid(): Boolean {
    return  Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
