package com.theguardian.entity.error

import com.theguardian.entity.error.AppError

class CallException(
    errorMessage: String? = null,
    val error: AppError
) : Exception(errorMessage) {
    constructor(error: AppError) : this(null, error)
}