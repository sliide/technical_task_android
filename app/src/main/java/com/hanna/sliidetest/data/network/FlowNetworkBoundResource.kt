package com.hanna.sliidetest.data.network

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.*


/**
 * Based on the logic implemented here: (NetworkBoundResource)
 * https://github.com/android/architecture-components-samples/blob/main/GithubBrowserSample/app/src/main/java/com/android/example/github/repository/NetworkBoundResource.kt
 */
abstract class FlowNetworkBoundResource<ResultType, RequestType> {

    fun asFlow() = flow<Resource<ResultType>> {
        emit(Resource.loading(null))
        loadFromDb().take(1).collect { dbValue ->
            emit(Resource.loading(dbValue))
            if (shouldFetch()) {
                fetchFromNetwork().take(1).collect { apiResponse ->
                    when (apiResponse) {
                        is ApiSuccessResponse -> {
                            saveNetworkResult(processResponse(apiResponse.body))
                            loadFromDb().collect {
                                emit(Resource.success(it))//continuously observe the changes
                            }
                        }
                        is ApiErrorResponse -> {
                            val data = loadFromDb().first()
                            emit(Resource.error(apiResponse.errorMessage, data))
                        }
                        is ApiEmptyResponse -> {
                            saveNetworkResult(null)
                            emit(Resource.success(null))
                        }
                    }
                }
            } else {
                emit(Resource.success(dbValue))
            }
        }
    }

    protected open fun onFetchFailed() {
        // Implement in sub-classes to handle errors
    }

    @WorkerThread
    protected open fun processResponse(response: RequestType) = response

    @WorkerThread
    protected abstract suspend fun saveNetworkResult(item: RequestType?)

    @MainThread
    protected abstract fun shouldFetch(): Boolean

    @MainThread
    protected abstract suspend fun loadFromDb(): Flow<ResultType>

    @MainThread
    protected abstract suspend fun fetchFromNetwork(): Flow<ApiResponse<RequestType>>
}
