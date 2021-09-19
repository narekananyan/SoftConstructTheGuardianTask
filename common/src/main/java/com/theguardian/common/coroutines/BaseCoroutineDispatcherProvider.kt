package com.theguardian.common.coroutines

import com.theguardian.common.coroutines.CoroutineDispatcherProvider
import kotlinx.coroutines.Dispatchers

internal class BaseCoroutineDispatcherProvider : CoroutineDispatcherProvider {
    override val main by lazy { Dispatchers.Main }
    override val io by lazy { Dispatchers.IO }
}