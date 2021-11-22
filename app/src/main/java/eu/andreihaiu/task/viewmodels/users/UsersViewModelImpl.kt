package eu.andreihaiu.task.viewmodels.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import eu.andreihaiu.task.data.base.ApiResponse
import eu.andreihaiu.task.data.models.Users
import eu.andreihaiu.task.data.repos.UsersRepository
import eu.andreihaiu.task.util.extensions.SingleLiveEvent
import eu.andreihaiu.task.util.extensions.safeLet
import eu.andreihaiu.task.util.status.Status
import eu.andreihaiu.task.viewmodels.base.BaseAndroidViewModelImpl
import kotlinx.coroutines.launch
import timber.log.Timber

class UsersViewModelImpl(
    private val usersRepository: UsersRepository
) : UsersViewModel, BaseAndroidViewModelImpl() {

    override val users = MutableLiveData<List<Users>>()
    override val userEvent = SingleLiveEvent<UserEvent>()

    init {
        getUsers()
    }

    override fun getUsers() {
        setStatus(Status.LOADING)
        viewModelScope.launch {
            when (val response = usersRepository.getUsers()) {
                is ApiResponse.Success -> {
                    Timber.d("Get Users Success, size: ${response.value.size}")
                    setStatus(Status.SUCCESS)
                    users.value = response.value
                }
                is ApiResponse.Error -> {
                    Timber.d("Get Users Error")
                    setStatus(Status.ERROR)
                    setErrorThrowable(Throwable(response.error))
                }
            }
        }
    }

    override fun addUser(userName: String?, userEmail: String?, userGender: String?) {
        Timber.d("Add user: $userName, $userEmail, $userGender")
        safeLet(userName, userEmail, userGender) { name, email, gender ->
            viewModelScope.launch {
                when (val response = usersRepository.addUser(name, email, gender)) {
                    is ApiResponse.Success -> {
                        Timber.d("Add User Success")
                        getUsers()
                        userEvent.value = UserEvent.ADD_SUCCES
                    }
                    is ApiResponse.Error -> {
                        Timber.d("Add User Error")
                        setStatus(Status.ERROR)
                        setErrorThrowable(Throwable(response.error))
                    }
                }
            }
        }
    }

    override fun deleteUser(userId: Long?) {
        Timber.d("Delete User: $userId")
        userId?.let { id ->
            setStatus(Status.LOADING)
            viewModelScope.launch {
                when (val response = usersRepository.deleteUser(id)) {
                    is ApiResponse.Success -> {
                        if (response.value.code() == 204) {
                            Timber.d("Delete User Success")
                            getUsers()
                            userEvent.value = UserEvent.DELETE_SUCCES
                        } else {
                            Timber.d("Delete User Error")
                            setStatus(Status.ERROR)
                        }
                    }
                    is ApiResponse.Error -> {
                        Timber.d("Delete User Error")
                        setStatus(Status.ERROR)
                        setErrorThrowable(Throwable(response.error))
                    }
                }
            }
        }
    }
}