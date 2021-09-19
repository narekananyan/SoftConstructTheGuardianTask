package com.theguardian.data.executor

import com.theguardian.entity.error.AppError
import com.theguardian.entity.error.NetworkError.*
import com.theguardian.entity.error.Unknown
import java.io.IOException
import retrofit2.HttpException

private const val HTTP_CODE_BAD_REQUEST = 400
private const val HTTP_CODE_AUTH_ERROR = 401
private const val HTTP_CODE_CONFLICT = 409
private const val HTTP_CODE_INTERNAL_SERVER_ERROR = 500
private const val HTTP_CODE_SERVICE_UNAVAILABLE = 503

class CallErrorMapper : (Exception) -> AppError {

    override fun invoke(error: Exception): AppError {
        return when (error) {
            is HttpException -> mapHttpCodeError(error)
            is IOException -> Connection
            else -> Unknown
        }
    }

    private fun mapHttpCodeError(httpException: HttpException) = when (httpException.code()) {
        HTTP_CODE_INTERNAL_SERVER_ERROR -> ServerInternal
        HTTP_CODE_AUTH_ERROR -> Authentication
        HTTP_CODE_CONFLICT -> Conflict
        HTTP_CODE_BAD_REQUEST -> BadRequest
        HTTP_CODE_SERVICE_UNAVAILABLE -> Connection
        else -> Unknown
    }
}