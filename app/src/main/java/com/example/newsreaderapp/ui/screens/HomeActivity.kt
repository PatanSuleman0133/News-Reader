package com.example.newsreaderapp.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.newsreaderapp.R
import com.example.newsreaderapp.databinding.ActivityHomeBinding
import com.example.newsreaderapp.ui.auth.LoginActivity
import com.example.newsreaderapp.utils.SecurePreferences
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var securePreferences: SecurePreferences

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        securePreferences = SecurePreferences(this)

        binding.logoutButton.setOnClickListener { logout() }

        val navView: BottomNavigationView = binding.bottomNavigationView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val navController = navHostFragment?.navController

        navController?.let { controller ->
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_tech,
                    R.id.nav_saved,
                    R.id.nav_business,
                    R.id.nav_sports
                )
            )
            navView.setupWithNavController(controller)
        }

    }

    private fun logout() {
        securePreferences.deleteData("user_email")
        auth.signOut()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
