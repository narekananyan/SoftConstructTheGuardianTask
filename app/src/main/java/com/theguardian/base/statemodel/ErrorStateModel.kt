package com.theguardian.base.statemodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ErrorStateModel{

    @Parcelize
    class DialogErrorStateModel(
        val tag: String = "",
        val title: CharSequence? = null,
        val message: CharSequence,
        val positiveButtonTitle: CharSequence? = null,
        val negativeButtonTitle: CharSequence? = null,
        val neutralButtonTitle: CharSequence? = null
    ) : ErrorStateModel(), Parcelable

    @Parcelize
    class SnackbarErrorStateModel(
        val message: String
    ) : ErrorStateModel(), Parcelable
}

