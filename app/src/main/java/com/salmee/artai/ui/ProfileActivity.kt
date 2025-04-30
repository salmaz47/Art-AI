package com.salmee.artai.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels // Use this import
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide // Use Glide for image loading
import com.salmee.artai.AboutActivity
import com.salmee.artai.ModelActivity
import com.salmee.artai.register.LoginActivity
import com.salmee.artai.R
import com.salmee.artai.data.repository.AuthRepositoryImpl
import com.salmee.artai.data.repository.UserRepositoryImpl // Import UserRepository
import com.salmee.artai.databinding.ActivityProfileBinding
import com.salmee.artai.presentation.viewmodel.UserViewModel // Need a UserViewModel
import com.salmee.artai.utils.ViewModelFactory

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userViewModel: UserViewModel // ViewModel for user data
    private var isGuest: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Determine guest status from SharedPreferences
        isGuest = SharedPreferencesHelper.isGuestMode(applicationContext)

        // --- ViewModel Setup ---
        // Pass context to ViewModelFactory
        val factory = ViewModelFactory(context = applicationContext)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        setupUIBasedOnAuthStatus()
        setupClickListeners()
        observeViewModel()

        if (!isGuest) {
            userViewModel.fetchUserProfile() // Fetch profile if logged in
        }
    }

    private fun setupUIBasedOnAuthStatus() {
        if (isGuest) {
            binding.nameText.text = "Guest User"
            binding.profileImage.setImageResource(R.drawable.profile) // Default guest image
            binding.signoutBtn.text = "Sign In"
            // Disable features unavailable to guests
            binding.favoriteBtn.isEnabled = false
            binding.favoriteBtn.alpha = 0.5f // Visually indicate disabled state
            // Add similar logic for other guest-restricted features if any
        } else {
            binding.nameText.text = "Loading..." // Placeholder while fetching
            binding.profileImage.setImageResource(R.drawable.profile) // Placeholder image
            binding.signoutBtn.text = "Sign Out"
            binding.favoriteBtn.isEnabled = true
            binding.favoriteBtn.alpha = 1.0f
        }
    }

    private fun observeViewModel() {
        userViewModel.userProfile.observe(this) { result ->
            result.fold(
                onSuccess = { user ->
                    if (!isGuest) { // Ensure we don't update UI if user logs out while fetching
                        binding.nameText.text = user.name
                        // Load avatar using Glide
                        Glide.with(this)
                            .load(user.avatarUrl ?: R.drawable.profile) // Load avatar or default
                            .placeholder(R.drawable.profile) // Use a default placeholder
                            .error(R.drawable.error_placeholder) // Use error placeholder
                            .circleCrop() // Make it circular if desired
                            .into(binding.profileImage)
                        // Update gender-specific image if needed based on user data or prefs
                        // val gender = SharedPreferencesHelper.getGender(applicationContext)
                        // val profileImageRes = if (gender == "male") R.drawable.boy_profile else R.drawable.girl_profile
                        // binding.profileImage.setImageResource(profileImageRes) 
                    }
                },
                onFailure = { exception ->
                    if (!isGuest) {
                        Toast.makeText(this, "Failed to load profile: ${exception.message}", Toast.LENGTH_SHORT).show()
                        binding.nameText.text = "Error loading profile"
                    }
                }
            )
        }

        userViewModel.logoutResult.observe(this) { success ->
            if (success) {
                navigateToLogin()
            } else {
                // Handle logout failure? Unlikely with current implementation
                Toast.makeText(this, "Logout failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupClickListeners() {
        binding.signoutBtn.setOnClickListener {
            if (isGuest) {
                navigateToLogin() // Guest clicks "Sign In"
            } else {
                userViewModel.logout() // Logged-in user clicks "Sign Out"
            }
        }

        binding.aboutBtn.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        binding.favoriteBtn.setOnClickListener {
            if (isGuest) {
                showLoginRequiredMessage()
            } else {
                // Navigate to FavoriteActivity for logged-in users
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
        }

        // --- Navigation Bar --- 
        binding.navigationBar.profileButton.setOnClickListener {
            // Already in ProfileActivity, maybe refresh or do nothing?
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            // No need to restart the activity: startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.navigationBar.homeButton.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            startActivity(Intent(this, CategoryActivity::class.java))
            // finish() // Decide if ProfileActivity should finish when going home
        }
        binding.navigationBar.centerIcon.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            startActivity(Intent(this, ModelActivity::class.java))
            // finish() // Decide if ProfileActivity should finish when going to ModelActivity
        }
    }

    private fun showLoginRequiredMessage() {
        Toast.makeText(this, "Please log in to use this feature.", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToLogin() {
        SharedPreferencesHelper.clearAll(applicationContext) // Clear prefs on navigating back to login
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    // Removed unused methods like loadUserData, setupGuestMode, setupUserProfile as logic is now in setupUIBasedOnAuthStatus and observeViewModel
    // Removed resetUI as logout navigation handles clearing state
}

