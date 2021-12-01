package ru.santaev.techtask.utils

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import ru.santaev.techtask.TechTaskApplication
import ru.santaev.techtask.di.AppComponent

val Activity.appComponent: AppComponent
    get() = (application as TechTaskApplication).component

val Fragment.appComponent: AppComponent
    get() = (requireActivity().application as TechTaskApplication).component

fun Fragment.setupToolbar(toolbar: Toolbar) {
    (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
}