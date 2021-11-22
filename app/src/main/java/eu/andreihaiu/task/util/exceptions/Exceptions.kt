package eu.andreihaiu.task.util.exceptions


class ApiCallException(
    exceptionMessage: String?,
    log: String
) : BaseException(exceptionMessage, log)


abstract class BaseException(
    exceptionMessage: String?,
    val log: String
) : Throwable(exceptionMessage) {

    fun reportException() {
//        FirebaseCrashlytics.getInstance().log(log)
//        FirebaseCrashlytics.getInstance().recordException(this)
    }
}