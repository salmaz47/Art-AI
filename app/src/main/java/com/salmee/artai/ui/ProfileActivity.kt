package com.salmee.artai.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.salmee.artai.AboutActivity
import com.salmee.artai.ModelActivity
import com.salmee.artai.register.LoginActivity
import com.salmee.artai.R
import com.salmee.artai.databinding.ActivityProfileBinding
import com.salmee.artai.presentation.viewmodel.UserViewModel
import com.salmee.artai.utils.ViewModelFactory

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userViewModel: UserViewModel
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
        val factory = ViewModelFactory(context = applicationContext)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        // Always set up the UI first based on locally stored data
        setupUIBasedOnStoredData()
        setupClickListeners()

        // Then observe for any updates from the API
        observeViewModel()

        // Only fetch profile from API if not in guest mode
        if (!isGuest) {
            userViewModel.fetchUserProfile()
        }
    }

    private fun setupUIBasedOnStoredData() {
        if (isGuest) {
            // Guest mode UI
            binding.nameText.text = "Guest User"
            binding.profileImage.setImageResource(R.drawable.profile) // Default guest image
            binding.signoutBtn.text = "Sign In"
            // Disable features unavailable to guests
            binding.favoriteBtn.isEnabled = false
            binding.favoriteBtn.alpha = 0.5f
        } else {
            // Logged-in user UI - use locally stored data first for immediate display
            val userName = SharedPreferencesHelper.getUserName(applicationContext)
            if (!userName.isNullOrEmpty()) {
                binding.nameText.text = userName
            } else {
                binding.nameText.text = "Loading..."
            }

            // Set profile image based on stored gender
            setProfileImageBasedOnGender()

            binding.signoutBtn.text = "Sign Out"
            binding.favoriteBtn.isEnabled = true
            binding.favoriteBtn.alpha = 1.0f
        }
    }

    private fun setProfileImageBasedOnGender() {
        val gender = SharedPreferencesHelper.getUserGender(applicationContext)
        when (gender) {
            getString(R.string.male) -> binding.profileImage.setImageResource(R.drawable.boy_profile)
            getString(R.string.female) -> binding.profileImage.setImageResource(R.drawable.girl_profile)
            else -> binding.profileImage.setImageResource(R.drawable.profile) // Default profile image
        }
    }

    private fun observeViewModel() {
        userViewModel.userProfile.observe(this) { result ->
            result.fold(
                onSuccess = { user ->
                    if (!isGuest) {
                        // Update stored name if different
                        if (user.name.isNotEmpty()) {
                            SharedPreferencesHelper.saveUserName(applicationContext, user.name)
                            binding.nameText.text = user.name
                        }

                        // Do not update the profile image here
                        // The gender-based image is already set in setupUIBasedOnStoredData()
                    }
                },
                onFailure = { exception ->
                    // Only show error if we don't already have a name displayed
                    if (!isGuest && binding.nameText.text == "Loading...") {
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
                Toast.makeText(this, getString(R.string.logout_failed), Toast.LENGTH_SHORT).show()
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
            // Already in ProfileActivity
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
        }
        binding.navigationBar.homeButton.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            startActivity(Intent(this, CategoryActivity::class.java))
        }
        binding.navigationBar.centerIcon.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            startActivity(Intent(this, ModelActivity::class.java))
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
}