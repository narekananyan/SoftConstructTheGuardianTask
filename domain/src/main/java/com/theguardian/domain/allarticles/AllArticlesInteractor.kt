package com.theguardian.domain.allarticles

import com.theguardian.entity.localmodels.ArticleUI
import com.theguardian.entity.roommodel.Article
import kotlinx.coroutines.flow.Flow

interface AllArticlesInteractor {
    suspend fun getArticles()
    fun getArticlesFromDB(): Flow<List<Article>>
    suspend fun getArticleUIMapper(article: List<Article>): List<ArticleUI>
    suspend fun deleteAllArticles()
}