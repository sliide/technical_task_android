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
    val userList = MutableLiveData<List<UserModel>>()
    val singleUser = MutableLiveData<UserModel>()
    val deleteUser = MutableLiveData<UserModel>()
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
            val insertinDb = localRepository.insertUserListInDB(list)
            val insertedInList = localRepository.getAllItemsInDB()
            withContext(Dispatchers.Main) {
                if (insertedInList != null && insertedInList.size > 0) {
                    userList.postValue(insertedInList)
                    loading.value = false
                } else {
                    onError("Error : No items found ")
                }
            }
        }
    }

    fun createNewUser(token : String,user: UserModel) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = mainRepository.createNewUser(token)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.code() == 201) {
                    insertUserInDB(user)
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    fun insertUserInDB(user: UserModel) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val insertinDb = localRepository.insertSingleUserInDB(user)
            withContext(Dispatchers.Main) {
                if (insertinDb != null) {
                    singleUser.postValue(user)
                    loading.value = false
                } else {
                    onError("Error : No items found ")
                }
            }
        }
    }

    fun deleteUser(token : String,user: UserModel) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = mainRepository.deleteUser(user.id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.code() == 204) {
                    deletetUserFromDB(user)
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    fun deletetUserFromDB(user: UserModel) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val insertinDb = localRepository.deleteUserFromDB(user)
            withContext(Dispatchers.Main) {
                if (insertinDb != null) {
                    deleteUser.postValue(user)
                    loading.value = false
                } else {
                    onError("Error : No items found ")
                }
            }
        }
    }

}