package com.theguardian.base.error

import com.theguardian.entity.error.CallException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

typealias ErrorHandlerFunction = (CallException) -> Unit

open class BaseCoroutineErrorHandler(
    private val exceptionHandler: ErrorHandlerFunction? = null
) : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
    override fun handleException(context: CoroutineContext, exception: Throwable) {
        if (exception is CallException) {
            exceptionHandler?.invoke(exception)
        }
    }
}