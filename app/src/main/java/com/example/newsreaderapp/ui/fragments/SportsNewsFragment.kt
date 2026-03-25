package com.example.newsreaderapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsreaderapp.R
import com.example.newsreaderapp.adapter.NewsAdapter
import com.example.newsreaderapp.adapter.SavedNewsAdapter
import com.example.newsreaderapp.database.NewsDatabase
import com.example.newsreaderapp.databinding.FragmentSavedArticlesBinding
import com.example.newsreaderapp.databinding.FragmentSportsNewsBinding
import com.example.newsreaderapp.databinding.FragmentTechNewsBinding
import com.example.newsreaderapp.model.Article
import com.example.newsreaderapp.model.ArticleEntity
import com.example.newsreaderapp.repository.NewsRepository
import com.example.newsreaderapp.repository.SavedNewsRepository
import com.example.newsreaderapp.utils.SecurePreferences
import com.example.newsreaderapp.viewmodel.NewsViewModel
import com.example.newsreaderapp.viewmodel.NewsViewModelFactory
import com.example.newsreaderapp.viewmodel.SavedNewsViewModel
import com.example.newsreaderapp.viewmodel.SavedNewsViewModelFactory


class SportsNewsFragment : Fragment() {

    private lateinit var binding: FragmentSportsNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewModel2: SavedNewsViewModel
    private lateinit var email: String
    private lateinit var securePreferences: SecurePreferences
    private var articleList: List<Article> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSportsNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        securePreferences = SecurePreferences(requireContext()) //sharedPref pull to insert
        email = securePreferences.getData("user_email") ?: "guest@example.com"

        Log.d("SportsArticlesFragment", "Email: $email")

        val repository = NewsRepository()  //for API
        val repository2 =
            SavedNewsRepository(NewsDatabase.getDatabase(requireContext()).articleDao(), email) //for RoomDb

        viewModel = ViewModelProvider(this, NewsViewModelFactory(repository))
            .get(NewsViewModel::class.java) //API ViewModel to Display

        viewModel2 = ViewModelProvider(this, SavedNewsViewModelFactory(repository2))
            .get(SavedNewsViewModel::class.java)  //RoomDb ViewModel to save

        newsAdapter = NewsAdapter(mutableListOf(), ::saveArticleToRoom, mutableSetOf())

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
        //fetch and update the date from API
        viewModel.news.observe(viewLifecycleOwner) { articles ->
            articleList = articles  // Store full article list
            newsAdapter.updateNews(articles)
        }

        // checking saved or not
        viewModel2.savedArticles.observe(viewLifecycleOwner) { savedArticles ->
            val savedTitles = savedArticles.map { it.title }.toMutableSet()
            newsAdapter.updateSavedArticles(savedTitles)
        }

        viewModel.fetchNews(getString(R.string.sports_key)) //sports news key for api

        // for placeholder
        binding.searchView.queryHint = getString(R.string.search_sports)
        binding.searchView.isIconified = false

        //search
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterArticles(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterArticles(newText)
                return true
            }
        })
    }

    private fun saveArticleToRoom(article: Article) {
        val articleEntity = ArticleEntity(
            title = article.title,
            description = article.description,
            url = article.url,
            urlToImage = article.urlToImage,
            userid = email   //added here

        )
        viewModel2.saveArticle(articleEntity) //saved here
        Toast.makeText(requireContext(), getString(R.string.article_saved), Toast.LENGTH_SHORT)
            .show()
    }

    // search
    private fun filterArticles(query: String?) {
        val filteredList = if (query.isNullOrBlank()) {
            articleList
        } else {
            articleList.filter { it.title.contains(query, ignoreCase = true) }
        }
        newsAdapter.updateNews(filteredList)
    }
}
