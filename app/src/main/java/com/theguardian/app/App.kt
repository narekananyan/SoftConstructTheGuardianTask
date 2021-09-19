package com.theguardian.app

import android.app.Application
import com.theguardian.common.di.commonModulesList
import com.theguardian.di.appModuleList
import com.theguardian.domain.di.domainModuleList
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModuleList  + commonModulesList + domainModuleList())
        }
    }
}