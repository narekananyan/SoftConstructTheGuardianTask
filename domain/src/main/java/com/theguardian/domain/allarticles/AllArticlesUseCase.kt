package com.theguardian.domain.allarticles

import com.theguardian.common.coroutines.CoroutineDispatcherProvider
import com.theguardian.data.datastore.AllArticlesRepository
import com.theguardian.entity.dto.Result
import com.theguardian.entity.localmodels.ArticleUI
import com.theguardian.entity.roommodel.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class AllArticlesUseCase(
    private val resultDtoToDaoMapper: (Result, Boolean) -> Article,
    private val articleToUI: (Article) -> ArticleUI,
    private val allArticlesRepository: AllArticlesRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) :
    AllArticlesInteractor {

    private var pageNumber = 1
    private var allPages = 500

    override suspend fun getArticles() {
        withContext(coroutineDispatcherProvider.io) {
            val result = allArticlesRepository.getArticles(pageNumber)
            pageNumber++
            allPages = result?.response?.pages ?: 1
            val favoriteArticlesIdFromDB = allArticlesRepository.getArticlesIdFromDB()
            result?.response?.results?.map {
                resultDtoToDaoMapper(
                    it, favoriteArticlesIdFromDB.contains(it.id)
                )
            }?.let {
                allArticlesRepository.insertArticles(
                    it
                )
            }
        }
    }

    override fun getArticlesFromDB(): Flow<List<Article>> {
        return allArticlesRepository.getArticlesFromDB()
    }

    override suspend fun getArticleUIMapper(article: List<Article>): List<ArticleUI> {
        return withContext(coroutineDispatcherProvider.io) { article.map { articleToUI(it) } }
    }

    override suspend fun deleteAllArticles() =
        withContext(coroutineDispatcherProvider.io) { allArticlesRepository.deleteAllArticles() }

}