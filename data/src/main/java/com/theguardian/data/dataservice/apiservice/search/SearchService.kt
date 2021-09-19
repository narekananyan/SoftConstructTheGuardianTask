package com.theguardian.data.dataservice.apiservice.search

import com.theguardian.entity.dto.ArticleDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search")
    suspend fun getArticles(
        @Query("page") page: Int,
    ): ArticleDTO
}