package com.example.newsreaderapp.ui.auth

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.newsreaderapp.R
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()

        val emailEditText: EditText = findViewById(R.id.et_email)
        val passwordEditText: EditText = findViewById(R.id.et_password)
        val registerButton: Button = findViewById(R.id.btn_register)
        val loginTextView: TextView = findViewById(R.id.tv_login)
        val nameEditText: EditText = findViewById(R.id.et_name)
        val contactEditText: EditText = findViewById(R.id.et_contact)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val name = nameEditText.text.toString().trim()
            val contact = contactEditText.text.toString().trim()
            registerUser(email, password, name, contact)
        }

        loginTextView.setOnClickListener {
            LoginNavigation()
        }

    }

    private fun LoginNavigation() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun registerUser(email: String, password: String, name: String, contact: String) {
        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || contact.isEmpty()) {
            Toast.makeText(this, getString(R.string.email_password_empty), Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, getString(R.string.valid_email), Toast.LENGTH_SHORT).show()
            return
        }
        if (password.length < 6) {
            Toast.makeText(this, getString(R.string.password_length), Toast.LENGTH_SHORT).show()
            return
        }
        if (contact.length < 10) {
            Toast.makeText(this, getString(R.string.contact_length), Toast.LENGTH_SHORT).show()
            return
        }
        if (name.length < 2) {
            Toast.makeText(this, getString(R.string.name_length), Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    showSuccessDialog()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.register_failed, task.exception?.message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun showSuccessDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_success, null)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()

        val okButton = dialogView.findViewById<Button>(R.id.btn_dialog_ok)
        okButton.setOnClickListener {
            dialog.dismiss()
            LoginNavigation()
        }
    }


}

