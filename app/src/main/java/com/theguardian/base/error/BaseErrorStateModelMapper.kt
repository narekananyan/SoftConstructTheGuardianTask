package com.theguardian.base.error

import android.content.res.Resources
import androidx.annotation.StringRes
import com.theguardian.R
import com.theguardian.base.statemodel.ErrorStateModel
import com.theguardian.base.statemodel.ErrorStateModel.*
import com.theguardian.entity.error.CallException
import com.theguardian.entity.error.NetworkError

class BaseErrorStateModelMapper(
    private val resources: Resources
) : (CallException) -> ErrorStateModel {
    override fun invoke(exception: CallException): ErrorStateModel {
        return when (exception.error) {
            is NetworkError.Connection -> DialogErrorStateModel(
                message = getString(
                    R.string.error_connection_message
                )
            )
            else -> DialogErrorStateModel(message = getString(R.string.error_unknown_message))
        }
    }

    private fun getString(@StringRes stringResId: Int): String {
        return resources.getString(stringResId)
    }
}