package com.sachin_sapkale_android_challenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android_test_maverick.UserModel
import com.android_test_maverick.local.repository.LocalRepository
import com.android_test_maverick.remote.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository, private val localRepository: LocalRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<UserModel>>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getLastPageNumbner(pageNumber : Int) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val findPLastPage = mainRepository.getSearchList(pageNumber)
            val page = findPLastPage.body()!!.meta.pagination.pages
            val response = mainRepository.getSearchList(page)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    getSearchListFromDB(response.body()!!.data)
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun getSearchListFromDB(list: List<UserModel>) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val insertinDb = localRepository.insertSingleItemInDB(list)
            val insertedInList = localRepository.getAllItemsInDB()
            withContext(Dispatchers.Main) {
                if (insertedInList != null && insertedInList.size > 0) {
                    movieList.postValue(insertedInList)
                    loading.value = false
                } else {
                    onError("Error : No items found ")
                }
            }
        }

    }

}