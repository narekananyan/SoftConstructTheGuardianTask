package com.theguardian.ui.fragment.allarticlesfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.theguardian.base.statemodel.ErrorStateModel
import com.theguardian.base.utils.launchSafe
import com.theguardian.base.viewmodel.BaseViewModel
import com.theguardian.base.viewmodel.SingleLiveEvent
import com.theguardian.domain.allarticles.AllArticlesInteractor
import com.theguardian.domain.favoritearticles.FavoriteArticlesInteractor
import com.theguardian.entity.error.CallException
import com.theguardian.entity.localmodels.ArticleUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AllArticlesFragmentViewModel(
    private val allArticlesFragmentInteractor: AllArticlesInteractor,
    private val favoriteArticlesInteractor: FavoriteArticlesInteractor,
    private val baseErrorStateModelMapper: (CallException) -> ErrorStateModel
) : BaseViewModel() {

    private val errorHandler: (CallException) -> Unit = {
        _error.value = baseErrorStateModelMapper(it)
    }

    private val _getArticlesError by lazy { SingleLiveEvent<String>() }
    val getArticlesError: LiveData<String> get() = _getArticlesError

    val allArticleLiveData: LiveData<List<ArticleUI>> =
        allArticlesFragmentInteractor.getArticlesFromDB()
            .map { allArticlesFragmentInteractor.getArticleUIMapper(it) }.asLiveData()

    fun getArticles() {
        _loading.value = true
        viewModelScope.launchSafe(errorHandlerBlock = errorHandler) {
            allArticlesFragmentInteractor.getArticles()
            }.invokeOnCompletion { _loading.value=false }
    }

    fun saveFavorite(favoriteArticle: ArticleUI) {
        viewModelScope.launchSafe(errorHandlerBlock = errorHandler) {
            favoriteArticlesInteractor.insertFavoriteArticle(favoriteArticle)
        }
    }

    fun deleteFavoriteArticle(favoriteArticle: ArticleUI) {
        viewModelScope.launchSafe(errorHandlerBlock = errorHandler) {
            favoriteArticlesInteractor.deleteFavoriteArticle(favoriteArticle)
        }
    }

}