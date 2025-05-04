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
    private lateinit var saveButton: View
    private lateinit var shareButton: View


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

        saveButton = binding.saveBtn
        shareButton = binding.shareBtn

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

        // Observe generation result (now Result<Image>)
        imageViewModel.generateResult.observe(this) { result ->
            result.fold(
                onSuccess = { generatedImage -> // Directly receive the Image object
                    Log.d("ModelActivity", "Image generated successfully: ${generatedImage.imageUrl}")
                    Glide.with(this)
                        .load(generatedImage.imageUrl)
                        .placeholder(R.drawable.loading_placeholder) // Add a placeholder drawable
                        .error(R.drawable.error_placeholder) // Add an error drawable
                        .into(binding.generatedImageView)
                    // Optionally add the new image to a local list or trigger a refresh
                    // Enable share & save
                    saveButton.setOnClickListener {
                        SharedPreferencesHelper.toggleFavorite(this, generatedImage)
                        Toast.makeText(this, "Toggled favorite", Toast.LENGTH_SHORT).show()
                    }

                    shareButton.setOnClickListener {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Check out this image:\n${generatedImage.imageUrl}")
                            type = "text/plain"
                        }

                        if (shareIntent.resolveActivity(packageManager) != null) {
                            startActivity(Intent.createChooser(shareIntent, "Share via"))
                        } else {
                            Toast.makeText(this, "No app available to share.", Toast.LENGTH_SHORT).show()
                        }
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

