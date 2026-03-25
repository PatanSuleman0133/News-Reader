package com.example.newsreaderapp.ui.screens

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsreaderapp.R
import com.example.newsreaderapp.ui.auth.LoginActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var logo: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        logo = findViewById(R.id.splash_logo)
        startLogoAnimation()
        navigateToMainScreen()

    }
    private fun startLogoAnimation() {
        val scaleUp: Animation? = AnimationUtils.loadAnimation(this, R.anim.scale_up)

        logo?.apply {
            startAnimation(scaleUp)
        }
    }

    private fun navigateToMainScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2500)
    }
}