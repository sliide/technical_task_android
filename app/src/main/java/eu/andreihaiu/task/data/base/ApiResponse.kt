package eu.andreihaiu.task.data.base

sealed class ApiResponse<out T> {
    data class Success<out T : Any>(val value: T) : ApiResponse<T>()
    data class Error(
        val code: Int? = null,
        val error: Exception? = null
    ) : ApiResponse<Nothing>()
}