package eu.andreihaiu.task.util.extensions

import eu.andreihaiu.task.data.base.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

suspend fun <T : Any> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): ApiResponse<T> {
    return withContext(dispatcher) {
        try {
            val result = apiCall.invoke()
            Timber.d("API Call Success")
            ApiResponse.Success(result)
        } catch (throwable: Exception) {
            Timber.d("API Call Error")
            when (throwable) {
                is HttpException -> {
                    val code = throwable.code()
                    ApiResponse.Error(code, throwable)
                }
                else -> ApiResponse.Error(error = throwable)
            }
        }
    }
}