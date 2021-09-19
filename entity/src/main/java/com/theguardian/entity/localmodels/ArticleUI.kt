package com.theguardian.entity.localmodels

import com.theguardian.entity.dto.Fields
import java.io.Serializable

data class ArticleUI(
    var id: String,
    var fields: Fields?,
    var webTitle: String?,
    var type: String?,
    var isFavorite: Boolean
) : Serializable
