package com.theguardian.ui.fragment.splashfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.theguardian.base.viewmodel.BaseViewModel
import com.theguardian.base.viewmodel.SingleLiveEvent
import com.theguardian.domain.allarticles.AllArticlesInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragmentViewModel(private val allArticlesFragmentInteractor: AllArticlesInteractor) :
    BaseViewModel() {

    private val _goToHome by lazy { SingleLiveEvent<Unit>() }
    val goToHome: LiveData<Unit> get() = _goToHome

    fun goToHomePage() {
        viewModelScope.launch {
            delay(2000)
            _goToHome.value = Unit
        }
    }

    fun deleteAllArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            allArticlesFragmentInteractor.deleteAllArticles()
        }
    }
}