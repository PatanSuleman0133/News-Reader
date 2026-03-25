package com.example.newsreaderapp.repository

import androidx.lifecycle.LiveData
import com.example.newsreaderapp.database.ArticleDao
import com.example.newsreaderapp.model.ArticleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SavedNewsRepository(private val articleDao: ArticleDao, private val userId: String) {

    val savedArticles: LiveData<List<ArticleEntity>> = articleDao.getAllSavedArticles(userId)

    suspend fun insertArticle(article: ArticleEntity): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                articleDao.insertArticle(article)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteArticle(article: ArticleEntity): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                articleDao.deleteArticle(article)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


