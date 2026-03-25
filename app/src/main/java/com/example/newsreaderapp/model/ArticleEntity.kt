package com.example.newsreaderapp.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_articles_two")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userid:String,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?
)



