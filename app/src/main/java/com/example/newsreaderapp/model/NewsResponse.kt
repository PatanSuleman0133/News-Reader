package com.example.newsreaderapp.model

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
data class Article(
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val url: String
)
