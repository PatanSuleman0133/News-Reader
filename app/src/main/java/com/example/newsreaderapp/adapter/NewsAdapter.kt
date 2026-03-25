package com.example.newsreaderapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsreaderapp.R
import com.example.newsreaderapp.model.Article
import com.example.newsreaderapp.ui.screens.DetailsActivity


class NewsAdapter(
    private var articles: MutableList<Article>,
    private val onSaveClick: (Article) -> Unit,
    private var savedTitles: MutableSet<String> // to know article saved or not
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.newsTitle)
        val description: TextView = view.findViewById(R.id.newsDescription)
        val image: ImageView = view.findViewById(R.id.newsImage)
        val saveButton: Button = view.findViewById(R.id.btnSave)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
        holder.description.text = article.description
        Glide.with(holder.itemView.context)
            .load(article.urlToImage)
            .placeholder(R.drawable.placeholder)
            .into(holder.image)

        // Check if article is already saved
        if (savedTitles.contains(article.title)) {
            holder.saveButton.isEnabled = false
            holder.saveButton.text = holder.itemView.context.getString(R.string.saved)
            holder.saveButton.setBackgroundColor(holder.itemView.context.getColor(R.color.gray))
        } else {
            holder.saveButton.isEnabled = true
            holder.saveButton.text = holder.itemView.context.getString(R.string.save)
            holder.saveButton.setBackgroundColor(holder.itemView.context.getColor(R.color.chocolate))
            //to add to saved
            holder.saveButton.setOnClickListener {
                holder.saveButton.isEnabled = false
                holder.saveButton.text = holder.itemView.context.getString(R.string.saved)
                holder.saveButton.setBackgroundColor(holder.itemView.context.getColor(R.color.gray))

                savedTitles.add(article.title) // to save to RoomDb
                onSaveClick(article)
            }
        }

        //details screen navigation
        holder.itemView.findViewById<TextView>(R.id.readMore).setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailsActivity::class.java).apply {
                putExtra("title", article.title)
                putExtra("description", article.description)
                putExtra("imageUrl", article.urlToImage)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = articles.size

    fun updateNews(newArticles: List<Article>) {
        articles.clear()
        articles.addAll(newArticles)
        notifyDataSetChanged()
    }

    fun updateSavedArticles(savedList: Set<String>) {
        savedTitles = savedList.toMutableSet()
        notifyDataSetChanged()
    }
}

