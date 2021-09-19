package com.theguardian.data.dataservice.sqlservice

import androidx.room.Database
import androidx.room.RoomDatabase
import com.theguardian.data.dataservice.sqlservice.dao.ArticleDao
import com.theguardian.data.dataservice.sqlservice.dao.FavoriteArticleDao
import com.theguardian.entity.roommodel.Article
import com.theguardian.entity.roommodel.Details
import com.theguardian.entity.roommodel.FavoriteArticle

@Database(
    entities = [Details::class, Article::class, FavoriteArticle::class],
    version = 3,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun favoriteArticleDao(): FavoriteArticleDao
}