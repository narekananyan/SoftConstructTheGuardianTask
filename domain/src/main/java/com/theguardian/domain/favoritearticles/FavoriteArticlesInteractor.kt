package com.theguardian.domain.favoritearticles

import com.theguardian.entity.localmodels.ArticleUI
import com.theguardian.entity.roommodel.FavoriteArticle
import kotlinx.coroutines.flow.Flow

interface FavoriteArticlesInteractor {
    fun getArticlesFromDB(): Flow<List<FavoriteArticle>>
    suspend fun insertArticles(articles: List<FavoriteArticle>)
    suspend fun insertFavoriteArticle(article: ArticleUI)
    suspend fun getArticleUIMapper(article: List<FavoriteArticle>): List<ArticleUI>
    suspend fun deleteFavoriteArticle(article: ArticleUI)
}