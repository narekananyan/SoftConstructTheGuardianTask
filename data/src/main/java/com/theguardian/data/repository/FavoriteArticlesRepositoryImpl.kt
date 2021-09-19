package com.theguardian.data.repository

import com.theguardian.data.dataservice.sqlservice.dao.FavoriteArticleDao
import com.theguardian.data.datastore.FavoriteArticlesRepository
import com.theguardian.entity.roommodel.FavoriteArticle
import kotlinx.coroutines.flow.Flow

internal class FavoriteArticlesRepositoryImpl(private val favoriteArticleDao: FavoriteArticleDao) :
    FavoriteArticlesRepository {

    override fun getArticlesFromDB(): Flow<List<FavoriteArticle>> =
        favoriteArticleDao.getFavoriteArticles()

    override suspend fun insertArticles(articles: List<FavoriteArticle>) =
        favoriteArticleDao.saveFavoriteArticleList(articles)

    override suspend fun insertFavoriteArticle(article: FavoriteArticle) =
        favoriteArticleDao.saveFavoriteArticle(article)

    override suspend fun deleteFavoriteArticle(favoriteArticle: FavoriteArticle) =
        favoriteArticleDao.deleteFavoriteArticle(favoriteArticle)
}