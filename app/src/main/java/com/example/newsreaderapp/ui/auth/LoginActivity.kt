package com.example.newsreaderapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.newsreaderapp.R
import com.example.newsreaderapp.ui.screens.HomeActivity
import com.example.newsreaderapp.utils.SecurePreferences
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var securePreferences: SecurePreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val arr = intArrayOf(1,20000,3000,14,5,16,10,500)
        var a=LargestNum(arr)
        Toast.makeText(this,a.toString(),Toast.LENGTH_SHORT).show()

        auth = FirebaseAuth.getInstance()

        securePreferences = SecurePreferences(this) //sharedPre
        val savedEmail = securePreferences.getData("user_email")

        Log.d("LoginActivity", "Saved Email: $savedEmail")

        if (savedEmail != null) {
            HomeNavigation()
            finish()
        }

        val emailEditText: EditText = findViewById(R.id.et_email)
        val passwordEditText: EditText = findViewById(R.id.et_password)
        val loginButton: Button = findViewById(R.id.btn_login)
        val signupTextView: TextView = findViewById(R.id.tv_signup)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            loginUser(email, password)
        }

        signupTextView.setOnClickListener {
            RegisterNavigation()
        }
    }

    private fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.email_password_empty), Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (password.length < 6) {
            Toast.makeText(this, getString(R.string.password_length), Toast.LENGTH_SHORT).show()
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, getString(R.string.valid_email), Toast.LENGTH_SHORT).show()
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    securePreferences.saveData("user_email", email)  // sharedPreference

                    Toast.makeText(this, getString(R.string.loginSuccess), Toast.LENGTH_SHORT)
                        .show()
                    HomeNavigation()
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.login_failed, task.exception?.message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun RegisterNavigation() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun HomeNavigation() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun LargestNum(arr:IntArray):Int{
        var res=arr[0];
        for (i in arr){
            if(res<i){
                res=i
            }
        }
        return res
    }

}