package com.example.newsreaderapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsreaderapp.adapter.SavedNewsAdapter
import com.example.newsreaderapp.database.NewsDatabase
import com.example.newsreaderapp.repository.SavedNewsRepository
import com.example.newsreaderapp.databinding.FragmentSavedArticlesBinding
import com.example.newsreaderapp.utils.SecurePreferences
import com.example.newsreaderapp.viewmodel.SavedNewsViewModel
import com.example.newsreaderapp.viewmodel.SavedNewsViewModelFactory

class SavedArticlesFragment : Fragment() {

    private lateinit var binding: FragmentSavedArticlesBinding
    private lateinit var viewModel: SavedNewsViewModel
    private lateinit var savedNewsAdapter: SavedNewsAdapter
    private lateinit var email: String
    private lateinit var securePreferences: SecurePreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        securePreferences = SecurePreferences(requireContext()) //sharedPref pull
        email = securePreferences.getData("user_email") ?: "guest@example.com"

        Log.d("SavedArticlesFragment", "Email: $email")

        val database = NewsDatabase.getDatabase(requireContext())
        val repository = SavedNewsRepository(database.articleDao(), email) //added here
        val factory = SavedNewsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(SavedNewsViewModel::class.java)

        savedNewsAdapter = SavedNewsAdapter { article ->
            viewModel.deleteArticle(article)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = savedNewsAdapter
        }

        //fetch and update the date from RoomDB
        viewModel.savedArticles.observe(viewLifecycleOwner) { articles ->
            savedNewsAdapter.submitList(articles)

            if (articles.isEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.tvNoData.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.tvNoData.visibility = View.GONE
            }
        }
    }
}


