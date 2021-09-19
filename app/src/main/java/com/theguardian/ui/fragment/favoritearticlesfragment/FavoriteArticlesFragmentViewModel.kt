package com.theguardian.ui.fragment.favoritearticlesfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.theguardian.base.utils.launchSafe
import com.theguardian.base.viewmodel.BaseViewModel
import com.theguardian.domain.favoritearticles.FavoriteArticlesInteractor
import com.theguardian.entity.localmodels.ArticleUI
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FavoriteArticlesFragmentViewModel(private val favoriteArticlesInteractor: FavoriteArticlesInteractor) :
    BaseViewModel() {

    val favoriteArticleLiveData: LiveData<List<ArticleUI>> =
        favoriteArticlesInteractor.getArticlesFromDB()
            .map { favoriteArticlesInteractor.getArticleUIMapper(it) }.asLiveData()

    fun deleteFavoriteArticle(favoriteArticle: ArticleUI) {
        viewModelScope.launchSafe {
            favoriteArticlesInteractor.deleteFavoriteArticle(favoriteArticle)
        }
    }
}