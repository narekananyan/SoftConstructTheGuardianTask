package com.theguardian.data.di

import com.theguardian.data.dataservice.sqlservice.databaseModule
import com.theguardian.data.executor.serviceExecutorModule
import com.theguardian.data.repository.repoModule

val dataModulesList = listOf(
    httpModule,
    repoModule,
    serviceExecutorModule,
    databaseModule
)