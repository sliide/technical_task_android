//package com.android_test_maverick.utils
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.android_test_maverick.remote.repository.MainRepository
//import javax.inject.Inject
//
//class MyViewModelFactory @Inject constructor(private val repository: MainRepository): ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//            MainViewModel(this.repository) as T
//        } else {
//            throw IllegalArgumentException("ViewModel Not Found")
//        }
//    }
//}