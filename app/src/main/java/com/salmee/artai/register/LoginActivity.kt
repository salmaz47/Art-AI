package com.salmee.artai.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider // Use ViewModelProvider
import com.salmee.artai.ui.ProfileActivity // Assuming this is the main activity after login/guest
import com.salmee.artai.data.repository.AuthRepositoryImpl
import com.salmee.artai.databinding.ActivityLoginBinding
import com.salmee.artai.presentation.viewmodel.AuthViewModel
import com.salmee.artai.utils.ViewModelFactory // Need a ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authViewModel: AuthViewModel
    // SharedPreferencesHelper is an object, access directly

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- ViewModel Setup ---
        // Create ViewModelFactory, passing application context
        val factory = ViewModelFactory(context = applicationContext)
        // Get ViewModel instance
        authViewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        // --- Login Button ---
        binding.loginBtn.setOnClickListener {
            val email = binding.emailField.text.toString().trim()
            val password = binding.passwordField.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Observe LiveData from ViewModel (Recommended pattern)
            authViewModel.loginResult.observe(this) { result ->
                result.fold(
                    onSuccess = {
                        // Login successful (token saved in repo)
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        navigateToMainApp()
                    },
                    onFailure = { exception ->
                        Toast.makeText(this, exception.message ?: "Login failed", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            authViewModel.login(email, password) // Trigger login
        }

        // --- Sign Up Link --- 
        binding.dontHaveAnAccount.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            // Consider if finish() is appropriate here - maybe not if user can go back
        }

        // --- Forgot Password --- 
        binding.forgetPassword.setOnClickListener {
            val email = binding.emailField.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Observe LiveData for reset result
            authViewModel.resetPasswordResult.observe(this) { result ->
                result.fold(
                    onSuccess = {
                        Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = { exception ->
                        Toast.makeText(this, exception.message ?: "Failed to send reset email", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            authViewModel.sendPasswordReset(email) // Trigger reset
        }

        // --- Continue as Guest --- 
        binding.continueAsGuest.setOnClickListener {
            SharedPreferencesHelper.setGuestMode(applicationContext, true)
            Toast.makeText(this, "Continuing as Guest", Toast.LENGTH_SHORT).show()
            navigateToMainApp()
        }

        // --- Social Login (Placeholder - requires backend integration) ---
        binding.googleBtn.setOnClickListener {
            // TODO: Implement Google Sign-In flow and backend /auth/google call
            Toast.makeText(this, "Google login not implemented yet", Toast.LENGTH_SHORT).show()
        }
        binding.facebookBtn.setOnClickListener {
            // TODO: Implement Facebook Sign-In flow and potential backend endpoint
            Toast.makeText(this, "Facebook login not implemented yet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToMainApp() {
        // Navigate to the main activity (e.g., ProfileActivity or a new MainActivity)
        val intent = Intent(this, ProfileActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clear back stack
        startActivity(intent)
        finish() // Finish LoginActivity
    }
}

