package com.salmee.artai.register

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.salmee.artai.R
import com.salmee.artai.data.repository.AuthRepositoryImpl
import com.salmee.artai.databinding.ActivitySignupBinding
import com.salmee.artai.presentation.viewmodel.AuthViewModel

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = AuthViewModel(AuthRepositoryImpl(FirebaseAuth.getInstance()))
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        binding.doneBtn.isEnabled = false // Disable button initially

        // Check if all fields are filled
        val textFields = listOf(
            binding.nameField, binding.emailField, binding.passwordField, binding.confirmPassword
        )
        textFields.forEach { editText ->
            editText.addTextChangedListener {
                binding.doneBtn.isEnabled = textFields.all { it.text.isNotEmpty() }
            }
        }

        binding.doneBtn.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            val name = binding.nameField.text.toString()
            val email = binding.emailField.text.toString()
            val password = binding.passwordField.text.toString()
            val gender = if (binding.checkBoxBoy.isChecked) "male" else "female"

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authViewModel.signup(email, password) { success, message ->
                if (success) {
                    authViewModel.sendEmailVerification { emailSent, errorMsg ->
                        if (emailSent) {
                            saveUserData(name, gender)
                            Toast.makeText(this, "Signup successful! Please verify your email.", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Signup successful, but email verification failed: $errorMsg", Toast.LENGTH_LONG).show()
                        }
                    }
                    finish()
                } else {
                    Toast.makeText(this, message ?: "Signup failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveUserData(name: String, gender: String) {
        val editor = sharedPreferences.edit()
        editor.putString("userName", name)
        editor.putString("userGender", gender)
        editor.apply()
    }
}
