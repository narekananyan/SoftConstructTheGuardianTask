package com.theguardian.entity.error

sealed class NetworkError : AppError {
    object ServerInternal : NetworkError()
    object Authentication : NetworkError()
    object Connection : NetworkError()
    object Conflict : NetworkError()
    object BadRequest : NetworkError()
}