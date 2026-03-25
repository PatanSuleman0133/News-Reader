package com.example.newsreaderapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsreaderapp.R
import com.example.newsreaderapp.model.ArticleEntity

class SavedNewsAdapter(
    private val onDeleteClick: (ArticleEntity) -> Unit
) : ListAdapter<ArticleEntity, SavedNewsAdapter.SavedNewsViewHolder>(DIFF_CALLBACK) {

    class SavedNewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.newsTitle)
        val description: TextView = view.findViewById(R.id.newsDescription)
        val image: ImageView = view.findViewById(R.id.newsImage)
        val deleteButton: ImageView = view.findViewById(R.id.deleteSavedNews)

        fun bind(article: ArticleEntity, onDeleteClick: (ArticleEntity) -> Unit) {
            title.text = article.title
            description.text = article.description

            Glide.with(itemView.context)
                .load(article.urlToImage)
                .placeholder(R.drawable.placeholder)
                .into(image)

            deleteButton.setOnClickListener {
                onDeleteClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_saved_news, parent, false)
        return SavedNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article, onDeleteClick)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleEntity>() {
            override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ArticleEntity,
                newItem: ArticleEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

