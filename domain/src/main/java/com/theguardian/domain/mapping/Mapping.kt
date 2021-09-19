package com.theguardian.domain.mapping

import com.theguardian.entity.dto.Result
import com.theguardian.entity.localmodels.ArticleUI
import com.theguardian.entity.roommodel.Article
import com.theguardian.entity.roommodel.FavoriteArticle

class ArticleModelMapper : ( Result,Boolean) -> Article {
    override fun invoke(
        result: Result,
        isFavorite:Boolean
    ): Article {
        return Article(
            id = result.id,
            fields = result.fields,
            webTitle = result.webTitle,
            type = result.type,
            isFavorite = isFavorite
        )
    }
}

class ArticleToUiMapper : ( Article) -> ArticleUI {
    override fun invoke(
        article: Article,
    ): ArticleUI=
         ArticleUI(
            id = article.id,
            fields = article.fields,
            webTitle = article.webTitle,
            type = article.type,
            isFavorite = article.isFavorite
        )
}

class FavoriteToUiMapper : ( FavoriteArticle) -> ArticleUI {
    override fun invoke(
        article: FavoriteArticle,
    ): ArticleUI=
        ArticleUI(
            id = article.id,
            fields = article.fields,
            webTitle = article.webTitle,
            type = article.type,
            isFavorite = true
        )
}

class UiArticleToFavoriteRoomMapper : ( ArticleUI) -> FavoriteArticle {
    override fun invoke(
        article: ArticleUI,
    ): FavoriteArticle=
        FavoriteArticle(
            id = article.id,
            fields = article.fields,
            webTitle = article.webTitle,
            type = article.type,
        )
}

class UiArticleToArticleRoomMapper : ( ArticleUI) -> Article {
    override fun invoke(
        article: ArticleUI,
    ): Article =
        Article(
            id = article.id,
            fields = article.fields,
            webTitle = article.webTitle,
            type = article.type,
            isFavorite = article.isFavorite
        )
}