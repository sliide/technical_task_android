package com.devforfun.sliidetask.ui.users

import androidx.lifecycle.*
import com.devforfun.sliidetask.R
import com.devforfun.sliidetask.repository.UsersRepository
import com.devforfun.sliidetask.services.events.CreateUserResult
import com.devforfun.sliidetask.services.events.DeleteUserResult
import com.devforfun.sliidetask.services.events.Result
import com.devforfun.sliidetask.services.events.UsersResult
import com.devforfun.sliidetask.services.model.User
import com.devforfun.sliidetask.ui.BaseViewModel
import com.devforfun.sliidetask.utils.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.inject


open class UsersViewModel : BaseViewModel(), LifecycleObserver {

    private val repository by inject<UsersRepository>()
    private val schedulerProvider by inject<BaseSchedulerProvider>()

    private val _usersResult = MutableLiveData<UsersResult>()
    val usersResult: LiveData<UsersResult> = _usersResult

    private val _deleteUsersResult = MutableLiveData<DeleteUserResult>()
    val deleteUserResult: LiveData<DeleteUserResult> = _deleteUsersResult

    private val _createUserResult = MutableLiveData<CreateUserResult>()
    val createUserResult: LiveData<CreateUserResult> = _createUserResult

    private val disposables = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun getUsers() {
        disposables.add(
            repository.getUsers().subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ userResult ->
                    if (userResult is Result.Success) {
                        _usersResult.value = UsersResult(success = userResult.data)
                    } else {
                        _usersResult.value =
                            UsersResult(errorStringId = R.string.error_fetching_users)
                    }
                }, {
                    _usersResult.value = UsersResult(errorStringId = R.string.error_fetching_users)
                })
        )
    }

    fun createUser(name : String, email : String) {
        val user = User(name = name, email = email, status = "Active", gender = "Male")
        disposables.add(repository.createUser(user)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                { userBodyResult->
                    if(userBodyResult is Result.Success) {
                        _createUserResult.value = CreateUserResult(success = userBodyResult.data)
                    } else {
                        _createUserResult.value = CreateUserResult(error = R.string.error_create_user)
                    }
                },
                { _createUserResult.value = CreateUserResult(error = R.string.error_create_user) })
        )
    }

    fun deleteUser(userId: Int) {
        disposables.add(repository.deleteUser(userId)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                { deleteUserResult->
                    if(deleteUserResult is Result.Success) {
                        _deleteUsersResult.value = DeleteUserResult(deletedUserId = deleteUserResult.data)
                    } else {
                        _deleteUsersResult.value = DeleteUserResult(error = R.string.error_delete_users)
                    }
                },
                { _deleteUsersResult.value = DeleteUserResult(error = R.string.error_delete_users) }
            ))
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun clearDisposables() {
        disposables.clear()
    }

    fun isInputValid(name: String, email: String): Boolean {
        if(name.isNotEmpty() && email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true
        }
        return false
    }

}