package eu.andreihaiu.task.util.status

enum class Status {
    SUCCESS,
    ERROR,
    LOADING;

    companion object {
        fun checkLoading(vararg args: Status?): Boolean {
            return args.fold(false, operation = { acc: Boolean, status: Status? ->
                return@fold acc || status == LOADING
            })
        }
    }
}

enum class ErrorType {
    SYNC_FAILED,
    SERVER_LOCAL,
    UNAUTHORIZED,
    DETAILS
}

enum class NetworkState {
    LOADING,
    LOADED,
    FAILED
}