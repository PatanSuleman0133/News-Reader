package com.example.newsreaderapp.ui.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.newsreaderapp.R
import com.example.newsreaderapp.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title") ?: getString(R.string.no_title)
        val description = intent.getStringExtra("description") ?: getString(R.string.no_description)
        val imageUrl = intent.getStringExtra("imageUrl")

        binding.newsTitle.text = title
        binding.newsDescription.text = description

        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder1)
            .into(binding.newsImage)

        binding.backButton.setOnClickListener {
            onBackPressed()
            Toast.makeText(this, "Back Button Clicked", Toast.LENGTH_SHORT).show();
        }
    }
}