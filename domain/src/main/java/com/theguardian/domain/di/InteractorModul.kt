package com.theguardian.domain.di

import com.theguardian.domain.allarticles.AllArticlesInteractor
import com.theguardian.domain.allarticles.AllArticlesUseCase
import com.theguardian.domain.favoritearticles.FavoriteArticlesInteractor
import com.theguardian.domain.favoritearticles.FavoriteArticlesUseCase
import org.koin.dsl.module

val interactorModule = module {
    single<AllArticlesInteractor> { AllArticlesUseCase(
        get(QualifierResultDtoToDaoMapper),
        get(QualifierArticleToUI),get(),get()) }
    single<FavoriteArticlesInteractor> { FavoriteArticlesUseCase(get(), get(),
        get(QualifierFavoriteToUI),
        get(QualifierUItoFavorite),
        get(QualifierFromUIToRoomModelArticle),get()) }
}