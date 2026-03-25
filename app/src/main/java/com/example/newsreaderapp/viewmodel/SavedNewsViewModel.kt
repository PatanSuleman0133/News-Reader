package com.example.newsreaderapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsreaderapp.model.ArticleEntity
import com.example.newsreaderapp.repository.SavedNewsRepository
import kotlinx.coroutines.launch

class SavedNewsViewModel(private val repository: SavedNewsRepository) : ViewModel() {

    val savedArticles: LiveData<List<ArticleEntity>> = repository.savedArticles

    fun saveArticle(article: ArticleEntity) {
        viewModelScope.launch {
            repository.insertArticle(article)
        }
    }

    fun deleteArticle(article: ArticleEntity) {
        viewModelScope.launch {
            repository.deleteArticle(article)
            repository.savedArticles
        }
    }
}




