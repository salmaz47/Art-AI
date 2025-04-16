package com.salmee.artai.register

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.salmee.artai.ui.ProfileActivity
import com.salmee.artai.data.repository.AuthRepositoryImpl
import com.salmee.artai.databinding.ActivityLoginBinding
import com.salmee.artai.presentation.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = AuthViewModel(AuthRepositoryImpl(FirebaseAuth.getInstance()))
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        binding.loginBtn.setOnClickListener {
            val email = binding.emailField.text.toString()
            val password = binding.passwordField.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authViewModel.login(email, password) { success, message ->
                if (success) {
                    var isGuest = false
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, message ?: "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.dontHaveAnAccount.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }


            // Forgot Password Click Listener
        binding.forgetPassword.setOnClickListener {
                val email = binding.emailField.text.toString().trim()

                if (email.isEmpty()) {
                    Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                authViewModel.sendPasswordReset(email) { success, message ->
                    if (success) {
                        Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, message ?: "Failed to send reset email", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
}
