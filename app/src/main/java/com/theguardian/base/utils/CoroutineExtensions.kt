package com.theguardian.base.utils

import com.theguardian.base.error.BaseCoroutineErrorHandler
import com.theguardian.entity.error.CallException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

typealias ErrorHandlerFunction = (CallException) -> Unit

fun CoroutineScope.launchSafe(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    errorHandlerBlock: ErrorHandlerFunction? = null,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return this.launch(context + BaseCoroutineErrorHandler(errorHandlerBlock), start, block)
}