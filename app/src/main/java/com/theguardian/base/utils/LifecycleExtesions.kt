package com.theguardian.base.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observe(liveData: LiveData<T>?, action: (T) -> Unit) {
    liveData?.observe(this, Observer { action(it ?: return@Observer) })
}

fun LifecycleOwner.observeEmptyEvent(liveData: LiveData<*>?, action: () -> Unit) {
    liveData?.observe(this) { action() }
}