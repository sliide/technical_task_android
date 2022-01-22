package com.sachinsapkale.android_test_maverick.utils

import java.util.*

fun getRandomEmail(): String {

    val rnd = Random()
    val number = rnd.nextInt(999999)
    val email = String.format("%06d", number) +"@gmail.com"
    return email
}