package com.theguardian.data.dataservice.sqlservice.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.theguardian.entity.roommodel.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM Article")
    fun getArticles(): Flow<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticle(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticleList(articles: List<Article>)

    @Query("DELETE FROM Article")
    suspend fun deleteAllArticles()

    @Query("UPDATE Article SET isFavorite = not :isFavorite WHERE id = :id")
    suspend fun updateArticle(isFavorite: Boolean, id: String)

}