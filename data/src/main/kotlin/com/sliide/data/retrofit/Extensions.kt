package com.sliide.data.retrofit

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun <T> Result<T>.isSuccess(): Boolean {
    contract {
        returns(true) implies (this@isSuccess is Result.Success<T>)
    }
    return this is Result.Success
}

fun <T> Result<T>.asSuccess(): Result.Success<T> {
    return this as Result.Success<T>
}

fun <T> Result<T>.asFailure(): Result.Failure<*> {
    return this as Result.Failure<*>
}

@OptIn(ExperimentalContracts::class)
fun <T> Result<T>.isHttpError(): Boolean {
    contract {
        returns(true) implies (this@isHttpError is Result.Failure.HttpError)
    }
    return this is Result.Failure.HttpError
}


@OptIn(ExperimentalContracts::class)
fun <E : Throwable> Result.Failure<E>.isHttpError(): Boolean {
    contract {
        returns(true) implies (this@isHttpError is Result.Failure.HttpError)
    }
    return this is Result.Failure.HttpError
}

fun <E : Throwable> Result.Failure<E>.asHttpError(): Result.Failure<*> {
    return this as Result.Failure.HttpError
}