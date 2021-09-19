package com.theguardian.common.di

import com.theguardian.common.coroutines.BaseCoroutineDispatcherProvider
import com.theguardian.common.coroutines.CoroutineDispatcherProvider
import org.koin.dsl.module

val commonModulesList = module {
    factory<CoroutineDispatcherProvider> { BaseCoroutineDispatcherProvider() }
}