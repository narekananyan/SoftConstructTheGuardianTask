package com.theguardian.entity.dto


import com.squareup.moshi.Json

data class ArticleDTO(
    @Json(name = "response")
    var response: Response?
)