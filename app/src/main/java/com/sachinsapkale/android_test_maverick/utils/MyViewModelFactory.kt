package com.sachin_sapkale_android_challenge.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sachin_sapkale_android_challenge.remote.repository.MainRepository
import com.sachin_sapkale_android_challenge.viewmodel.MainViewModel
import javax.inject.Inject

class MyViewModelFactory @Inject constructor(private val repository: MainRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}