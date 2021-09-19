package com.theguardian.entity.dto

import com.squareup.moshi.Json

data class AccountDTO(
    @field:Json(name = "fname")
    val firstName: String? = null
)