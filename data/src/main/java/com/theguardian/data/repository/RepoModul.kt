package com.theguardian.data.repository

import com.theguardian.data.datastore.AllArticlesRepository
import com.theguardian.data.datastore.FavoriteArticlesRepository
import org.koin.dsl.module


internal val repoModule = module {
    single<AllArticlesRepository> { AllArticlesRepositoryImpl(get(), get(), get(), get()) }
    single<FavoriteArticlesRepository> { FavoriteArticlesRepositoryImpl(get()) }

}