package com.salmee.artai

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Toast // Import Toast
import androidx.activity.viewModels // Use this import
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.salmee.artai.databinding.ActivityModelBinding
import com.salmee.artai.presentation.viewmodel.ImageViewModel // Import ImageViewModel
import com.salmee.artai.ui.CategoryActivity
import com.salmee.artai.ui.ProfileActivity
import com.salmee.artai.utils.ViewModelFactory // Import ViewModelFactory

class ModelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModelBinding
    private lateinit var imageViewModel: ImageViewModel // ViewModel for image operations
    private var isGuest: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityModelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check guest status
        isGuest = SharedPreferencesHelper.isGuestMode(applicationContext)

        // --- ViewModel Setup ---
        val factory = ViewModelFactory(context = applicationContext)
        imageViewModel = ViewModelProvider(this, factory).get(ImageViewModel::class.java)

        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {
        // Navigation buttons
        binding.navigationBar.profileButton.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.navigationBar.homeButton.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }
        binding.navigationBar.centerIcon.setOnClickListener {
            // Already in ModelActivity, do nothing or refresh?
        }

        // Generate button (car icon)
        binding.carButton.setOnClickListener {
            if (isGuest) {
                Toast.makeText(this, "Please log in to generate images.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val prompt = binding.inputText.text.toString().trim()
            if (prompt.isEmpty()) {
                Toast.makeText(this, "Please enter a prompt", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Trigger generation via ViewModel
            imageViewModel.generateImage(prompt)
        }
    }

    private fun observeViewModel() {
        // Observe generation loading state
        imageViewModel.isGenerating.observe(this) { isLoading ->
            if (isLoading) {
                startCarAnimation() // Show loading animation
                binding.generatedImageView.setImageResource(0) // Clear previous image
                binding.carButton.isEnabled = false // Disable button while loading
                binding.inputText.isEnabled = false
            } else {
                stopCarAnimation() // Hide loading animation
                binding.carButton.isEnabled = true // Re-enable button
                binding.inputText.isEnabled = true
            }
        }

        // Observe generation result
        imageViewModel.generateResult.observe(this) { result ->
            result.fold(
                onSuccess = { response ->
                    // Handle response - assuming it might contain the image directly or need polling
                    if (response.image != null) {
                        Log.d("ModelActivity", "Image generated successfully: ${response.image.imageUrl}")
                        Glide.with(this)
                            .load(response.image.imageUrl)
                            .placeholder(R.drawable.loading_placeholder) // Add a placeholder drawable
                            .error(R.drawable.error_placeholder) // Add an error drawable
                            .into(binding.generatedImageView)
                    } else if (response.taskId != null) {
                        // TODO: Implement polling logic if backend is async and returns task ID
                        Log.d("ModelActivity", "Image generation started with task ID: ${response.taskId}")
                        Toast.makeText(this, "Image generation started...", Toast.LENGTH_SHORT).show()
                        // For now, just show a message
                    } else {
                         Log.w("ModelActivity", "Generation successful but no image or task ID received.")
                         Toast.makeText(this, "Generation finished, but no image found.", Toast.LENGTH_SHORT).show()
                    }
                },
                onFailure = { exception ->
                    Log.e("ModelActivity", "Image generation failed: ${exception.message}")
                    Toast.makeText(this, "Generation failed: ${exception.message}", Toast.LENGTH_LONG).show()
                    binding.generatedImageView.setImageResource(R.drawable.error_placeholder) // Show error placeholder
                }
            )
        }
    }

    // --- Car Animation Logic (Simplified) ---
    private var carAnimation: Animation? = null

    private fun startCarAnimation() {
        binding.movingCar.visibility = View.VISIBLE
        // Simple continuous animation (replace with original if preferred)
        carAnimation = TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, 0f,
            Animation.RELATIVE_TO_PARENT, 0.8f, // Move across 80% of parent width
            Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f
        ).apply {
            duration = 1500 // Adjust duration
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
        }
        binding.movingCar.startAnimation(carAnimation)
    }

    private fun stopCarAnimation() {
        binding.movingCar.clearAnimation()
        binding.movingCar.visibility = View.GONE
        carAnimation = null
    }

    // Removed direct OkHttp call (callGenerateApi)
    // Removed simulateApiCall
}

