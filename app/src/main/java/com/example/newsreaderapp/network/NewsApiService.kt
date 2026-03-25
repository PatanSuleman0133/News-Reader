package com.example.newsreaderapp.network

import com.example.newsreaderapp.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = "11d6582d4338407ea8428a1f5165d704",
        @Query("country") country: String = "us"
    ): Response<NewsResponse>
}