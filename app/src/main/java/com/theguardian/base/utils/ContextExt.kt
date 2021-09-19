package com.theguardian.base.utils

import android.app.Activity
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.theguardian.R

fun Activity.showSnack(
    view: View,
    message: String,
    backgroundColorId: Int = R.color.colorAccent,
    textColorId: Int = R.color.dark_pink
) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).apply {
        setBackgroundTint(ContextCompat.getColor(this@showSnack, backgroundColorId))
        setTextColor(ContextCompat.getColor(this@showSnack, textColorId))
        show()
    }
}