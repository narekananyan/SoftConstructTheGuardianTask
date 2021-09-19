package com.theguardian.data.repository

import com.theguardian.data.dataservice.apiservice.search.SearchService
import com.theguardian.data.dataservice.sqlservice.dao.ArticleDao
import com.theguardian.data.dataservice.sqlservice.dao.FavoriteArticleDao
import com.theguardian.data.datastore.AllArticlesRepository
import com.theguardian.data.executor.ServiceExecutor
import com.theguardian.entity.dto.ArticleDTO
import com.theguardian.entity.roommodel.Article
import kotlinx.coroutines.flow.Flow

internal class AllArticlesRepositoryImpl(
    private val retrofitService: SearchService,
    private val articleDao: ArticleDao,
    private val favoriteArticleDao: FavoriteArticleDao,
    private val apiExecutor: ServiceExecutor
) : AllArticlesRepository {

    override suspend fun getArticles(page: Int): ArticleDTO =
        apiExecutor.execute {
            retrofitService.getArticles(page)
        }

    override suspend fun getArticlesIdFromDB(): List<String> =
        favoriteArticleDao.getArticlesIdFromDB()

    override fun getArticlesFromDB(): Flow<List<Article>> = articleDao.getArticles()

    override suspend fun insertArticles(articles: List<Article>) =
        articleDao.saveArticleList(articles)

    override suspend fun deleteAllArticles() = articleDao.deleteAllArticles()

    override suspend fun updateArticle(article: Article) =
        articleDao.updateArticle(article.isFavorite, article.id)
}