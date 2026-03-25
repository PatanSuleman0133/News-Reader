package com.example.newsreaderapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsreaderapp.model.Article
import com.example.newsreaderapp.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {


    private val _news = MutableLiveData<List<Article>>()
    val news: LiveData<List<Article>> get() = _news

    fun fetchNews(category: String) {
        viewModelScope.launch {
            val response = repository.getNews(category)
            if (response.isSuccessful) {
                _news.postValue(response.body()?.articles)
            }
        }
    }
}

