package com.theguardian.entity

import com.theguardian.entity.error.CallException


sealed class Result<out S> {
    data class Success<S>(val data: S?) : Result<S>()
    data class Error<E>(val errors: CallException) : Result<E>()
}