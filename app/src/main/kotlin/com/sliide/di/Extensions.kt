package com.sliide.di

import android.content.Context
import com.sliide.App

fun Context.appProvider() = (applicationContext as App).appProvider