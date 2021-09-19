package com.theguardian.data.dataservice.sqlservice

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "GuardianTaskDB")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { provideDatabase(androidApplication()) }
    single { get<AppDatabase>().articleDao() }
    single { get<AppDatabase>().favoriteArticleDao() }
}