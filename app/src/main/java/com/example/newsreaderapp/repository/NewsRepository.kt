package com.example.newsreaderapp.repository

import android.util.Log
import com.example.newsreaderapp.model.NewsResponse
import com.example.newsreaderapp.network.RetrofitInstance
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class NewsRepository {
    suspend fun getNews(category: String): Response<NewsResponse> {
        return try {
            val response = RetrofitInstance.api.getNews(category)
            if (response.isSuccessful) {
                response
            } else {
                Log.e("NewsRepository", "Error: ${response.code()} - ${response.message()}")
                Response.error(response.code(), response.errorBody() ?: "".toResponseBody(null))
            }
        } catch (e: Exception) {
            Log.e("NewsRepository", "Network Error: ${e.localizedMessage}", e)
            Response.error(500, "".toResponseBody(null))
        }
    }
}


