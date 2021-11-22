package eu.andreihaiu.task.viewmodels.base

import androidx.lifecycle.*
import eu.andreihaiu.task.R
import eu.andreihaiu.task.util.constants.*
import eu.andreihaiu.task.util.exceptions.ApiCallException
import eu.andreihaiu.task.util.extensions.SingleLiveEvent
import eu.andreihaiu.task.util.status.NetworkState
import eu.andreihaiu.task.util.status.Status
import kotlinx.coroutines.cancel
import retrofit2.HttpException
import timber.log.Timber
import java.net.UnknownHostException


abstract class BaseAndroidViewModelImpl : ViewModel(), BaseViewModel {

    val errorMessage = SingleLiveEvent<Int>()

    override val appNavigation = SingleLiveEvent<AppNavigation>()
    private val _status = MutableLiveData<Status>()
    override val status: LiveData<Status>
        get() = _status.distinctUntilChanged()

    fun setErrorThrowable(throwable: Throwable, methodName: String? = null) {
        Timber.e(throwable)
        if (throwable is HttpException) {
            val errorLog = "${javaClass.simpleName} $methodName:" +
                    " ${throwable.code()} " +
                    " ${throwable.response()?.raw()?.request?.url}" +
                    "// ${throwable.message()}"

            val apiCallException = ApiCallException(throwable.message, errorLog)
            apiCallException.reportException()
        }

        val errorResource = when (throwable) {
            is HttpException -> {
                when (throwable.code()) {
                    UNAUTHORIZED -> R.string.wrong_password_or_email
                    TOO_MANY_REQUESTS -> R.string.account_locked
                    ACCOUNT_LOCKED -> R.string.account_locked
                    PRECONDITION_FAILED -> R.string.already_unlocked
                    NOT_FOUND -> R.string.wrong_password_or_email
                    BAD_REQUEST -> R.string.bad_request
                    else -> R.string.error_http
                }
            }
            is UnknownHostException -> R.string.error_no_internet
            else -> R.string.error_general
        }

        errorMessage.postValue(errorResource)
        setStatus(Status.ERROR)
    }

    fun setStatus(status: Status) {
        Timber.d("setStatus: $status from: ${this.javaClass.simpleName}")
        _status.value = status
    }

    override fun onCleared() {
        viewModelScope.coroutineContext.cancel()
        super.onCleared()
    }

    fun getStatusFromNetworkState(networkState: NetworkState?): Status? {
        return when (networkState) {
            NetworkState.LOADED -> Status.SUCCESS
            NetworkState.LOADING -> Status.LOADING
            NetworkState.FAILED -> Status.ERROR
            else -> null
        }
    }
}