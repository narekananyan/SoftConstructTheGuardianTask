package com.theguardian.domain.di

import com.theguardian.data.di.dataModulesList
import org.koin.core.module.Module

fun domainModuleList():List<Module> {
    return listOf(
        mapperModelModule,
        interactorModule) + dataModulesList
}