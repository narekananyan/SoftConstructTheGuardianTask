package com.theguardian.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theguardian.base.statemodel.ErrorStateModel

open class BaseViewModel : ViewModel() {
    protected val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    protected val _error = SingleLiveEvent<ErrorStateModel>()
    val error: LiveData<ErrorStateModel> get() = _error

}