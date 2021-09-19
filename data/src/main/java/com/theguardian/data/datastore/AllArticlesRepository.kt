package com.theguardian.data.datastore

import com.theguardian.entity.dto.ArticleDTO
import com.theguardian.entity.roommodel.Article
import kotlinx.coroutines.flow.Flow

interface AllArticlesRepository {
    suspend fun getArticles(page: Int): ArticleDTO?
    fun getArticlesFromDB(): Flow<List<Article>>
    suspend fun getArticlesIdFromDB(): List<String>
    suspend fun insertArticles(articles: List<Article>)
    suspend fun deleteAllArticles()
    suspend fun updateArticle(article: Article)
}