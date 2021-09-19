package com.theguardian.domain.favoritearticles

import com.theguardian.common.coroutines.CoroutineDispatcherProvider
import com.theguardian.data.datastore.AllArticlesRepository
import com.theguardian.data.datastore.FavoriteArticlesRepository
import com.theguardian.entity.localmodels.ArticleUI
import com.theguardian.entity.roommodel.Article
import com.theguardian.entity.roommodel.FavoriteArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class FavoriteArticlesUseCase(
    private val favoriteArticlesRepository: FavoriteArticlesRepository,
    private val allArticlesFragmentRepository: AllArticlesRepository,
    private val favoriteToUI: (FavoriteArticle) -> ArticleUI,
    private val uiToFavorite: ( ArticleUI) -> FavoriteArticle,
    private val fromUIToRoomModelArticle:(ArticleUI)->Article,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : FavoriteArticlesInteractor {

    override fun getArticlesFromDB(): Flow<List<FavoriteArticle>> =
        favoriteArticlesRepository.getArticlesFromDB()

    override suspend fun insertArticles(articles: List<FavoriteArticle>) =
        withContext(coroutineDispatcherProvider.io){favoriteArticlesRepository.insertArticles(articles)}

    override suspend fun getArticleUIMapper(article: List<FavoriteArticle>): List<ArticleUI> {
        return withContext(coroutineDispatcherProvider.io){article.map { favoriteToUI(it) }}
    }

    override suspend fun insertFavoriteArticle(article: ArticleUI) {
        withContext(coroutineDispatcherProvider.io){
            favoriteArticlesRepository.insertFavoriteArticle(uiToFavorite(article))
            allArticlesFragmentRepository.updateArticle(fromUIToRoomModelArticle(article))
        }
    }

    override suspend fun deleteFavoriteArticle(article: ArticleUI) {
        withContext(coroutineDispatcherProvider.io){
            favoriteArticlesRepository.deleteFavoriteArticle(uiToFavorite(article))
            allArticlesFragmentRepository.updateArticle(fromUIToRoomModelArticle(article))
        }
    }
}