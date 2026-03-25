package com.example.newsreaderapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsreaderapp.model.ArticleEntity

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity)

    @Delete
    suspend fun deleteArticle(article: ArticleEntity)

    @Query("SELECT * FROM saved_articles_two WHERE userId = :userId")
    fun getAllSavedArticles(userId: String): LiveData<List<ArticleEntity>>
}

