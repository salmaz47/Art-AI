package com.salmee.artai.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.salmee.artai.R
import com.salmee.artai.databinding.ActivitySignupBinding
import com.salmee.artai.presentation.viewmodel.AuthViewModel
import com.salmee.artai.utils.ViewModelFactory

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var authViewModel: AuthViewModel
    private var selectedGender: String? = null // Add gender tracking

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- ViewModel Setup ---
        val factory = ViewModelFactory(context = applicationContext)
        authViewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        binding.doneBtn.isEnabled = false // Disable button initially

        // Check if all fields are filled
        val textFields = listOf(
            binding.nameField, binding.emailField, binding.passwordField, binding.confirmPassword
        )
        textFields.forEach { editText ->
            editText.addTextChangedListener {
                // Also check if passwords match
                val passwordsMatch = binding.passwordField.text.toString() == binding.confirmPassword.text.toString()
                binding.doneBtn.isEnabled = textFields.all { it.text.isNotEmpty() } &&
                        passwordsMatch && selectedGender != null // Add gender check

                if (binding.confirmPassword.text.isNotEmpty() && !passwordsMatch) {
                    binding.confirmPassword.error = "Passwords do not match"
                } else {
                    binding.confirmPassword.error = null // Clear error
                }
            }
        }

        // Setup gender selection with mutual exclusivity
        binding.checkBoxBoy.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkBoxGirl.isChecked = false
                selectedGender = "male"
                updateButtonState()
            } else if (!binding.checkBoxGirl.isChecked) {
                selectedGender = null
                updateButtonState()
            }
        }

        binding.checkBoxGirl.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkBoxBoy.isChecked = false
                selectedGender = "female"
                updateButtonState()
            } else if (!binding.checkBoxBoy.isChecked) {
                selectedGender = null
                updateButtonState()
            }
        }

        binding.doneBtn.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            val name = binding.nameField.text.toString().trim()
            val email = binding.emailField.text.toString().trim()
            val password = binding.passwordField.text.toString()
            val age = binding.age.text.toString()

            // Basic validation
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || age.isEmpty() || selectedGender == null) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != binding.confirmPassword.text.toString()) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save gender preference for profile picture
            SharedPreferencesHelper.saveUserGender(applicationContext, selectedGender!!)
            SharedPreferencesHelper.saveUserName(applicationContext, name)

            // Observe signup result from ViewModel
            authViewModel.signupResult.observe(this) { result ->
                result.fold(
                    onSuccess = {
                        Toast.makeText(this, "Signup successful! Please log in.", Toast.LENGTH_LONG).show()
                        // Navigate to LoginActivity after successful signup
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish() // Finish SignupActivity
                    },
                    onFailure = { exception ->
                        Toast.makeText(this, exception.message ?: "Signup failed", Toast.LENGTH_SHORT).show()
                    }
                )
            }
            // Trigger signup via ViewModel
            authViewModel.signup(name, email, password)
        }

        // --- Already have an account Link ---

        /*  binding.alreadyHaveAnAccount?.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Finish SignupActivity when going back to Login
        }*/
    }

    private fun updateButtonState() {
        val textFields = listOf(
            binding.nameField, binding.emailField, binding.passwordField, binding.confirmPassword
        )
        val passwordsMatch = binding.passwordField.text.toString() == binding.confirmPassword.text.toString()
        binding.doneBtn.isEnabled = textFields.all { it.text.isNotEmpty() } &&
                passwordsMatch && selectedGender != null
    }
}