package com.slide.test.exercise.users

import android.util.Log
import androidx.lifecycle.ViewModel
import com.slide.test.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Stefan Halus on 18 May 2022
 */
@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {


    fun getUsers()  {
        getUsersUseCase.execute().subscribe({
            Log.d("Horray", it)
        },{
            Log.e("ERROR", it.stackTraceToString())
        })
    }
}