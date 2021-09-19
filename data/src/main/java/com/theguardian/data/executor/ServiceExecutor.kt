package com.theguardian.data.executor

import com.theguardian.common.coroutines.CoroutineDispatcherProvider
import com.theguardian.entity.error.AppError
import com.theguardian.entity.error.CallException
import kotlinx.coroutines.withContext

internal class ServiceExecutor(
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val errorMapper: (Exception) -> AppError
) {
    suspend fun <T> execute(block: suspend () -> T): T =
        withContext(coroutineDispatcherProvider.io) {
            try {
                block()
            } catch (e: Exception) {
                throw CallException(e.message, errorMapper(e))
            }
        }
}