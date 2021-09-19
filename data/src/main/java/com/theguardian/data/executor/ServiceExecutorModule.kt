package com.theguardian.data.executor

import com.theguardian.entity.error.AppError
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

private val QualifierErrorMapper = StringQualifier("QualifierErrorMapper")

val serviceExecutorModule = module {
    factory { ServiceExecutor(get(), get(QualifierErrorMapper)) }
    factory<(Exception) -> AppError>(QualifierErrorMapper) { CallErrorMapper() }
}