package com.theguardian.entity.dto

import com.squareup.moshi.Json
import java.io.Serializable

data class Fields(
    @Json(name = "thumbnail")
    var thumbnail: String?,
    @Json(name = "body")
    var body: String?
) : Serializable