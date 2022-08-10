package com.sliide.demoapp.utils.response

sealed class DataState<out R> {
    data class Success<out T>(val data: T): DataState<T>()
    data class Error(val error: String): DataState<Nothing>()
}

