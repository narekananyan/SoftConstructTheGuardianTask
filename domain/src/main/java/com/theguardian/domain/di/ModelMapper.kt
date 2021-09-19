package com.theguardian.domain.di

import com.theguardian.domain.mapping.*
import com.theguardian.entity.dto.Result
import com.theguardian.entity.localmodels.ArticleUI
import com.theguardian.entity.roommodel.Article
import com.theguardian.entity.roommodel.FavoriteArticle
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

internal val QualifierResultDtoToDaoMapper = StringQualifier("resultDtoToDaoMapper")
internal val QualifierArticleToUI = StringQualifier("articleToUI")
internal val QualifierFavoriteToUI = StringQualifier("favoriteToUI")
internal val QualifierUItoFavorite = StringQualifier("uItoFavorite")
internal val QualifierFromUIToRoomModelArticle = StringQualifier("fromUIToRoomModelArticle")


internal val mapperModelModule = module {
    factory<(Result, Boolean) -> Article>(QualifierResultDtoToDaoMapper) { ArticleModelMapper() }
    factory<(Article) -> ArticleUI>(QualifierArticleToUI) { ArticleToUiMapper() }
    factory<(FavoriteArticle) -> ArticleUI>(QualifierFavoriteToUI) { FavoriteToUiMapper() }
    factory<(ArticleUI) -> FavoriteArticle>(QualifierUItoFavorite) { UiArticleToFavoriteRoomMapper() }
    factory<(ArticleUI) -> Article>(QualifierFromUIToRoomModelArticle) { UiArticleToArticleRoomMapper() }

}