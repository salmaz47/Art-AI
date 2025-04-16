package com.salmee.artai.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.salmee.artai.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.salmee.artai.AboutActivity
import com.salmee.artai.ModelActivity
import com.salmee.artai.register.LoginActivity
import com.salmee.artai.R
import com.salmee.artai.data.repository.AuthRepositoryImpl
import com.salmee.artai.presentation.viewmodel.AuthViewModel
import com.salmee.artai.presentation.viewmodel.ViewModelFactory

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val authViewModel: AuthViewModel by viewModels { ViewModelFactory(AuthRepositoryImpl(FirebaseAuth.getInstance())) }

    private var isGuest = true

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isGuest = intent.getBooleanExtra("IS_GUEST", false)  // Check if user is a guest

        if (isGuest) {
            setupGuestMode()
        } else {
            setupUserProfile()
        }

        binding.signoutBtn.setOnClickListener {
            if (isGuest) {
                navigateToLogin()
            } else {
                authViewModel.logout()
                resetUI()
            }
        }
        binding.aboutBtn.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)

        }

        // Prevent guests from using favorite and save features
        binding.favoriteBtn.setOnClickListener {
            showLoginRequiredMessage()
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)

        }
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        loadUserData()


        binding.navigationBar.profileButton.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            val i = Intent(this, ProfileActivity::class.java)
            startActivity(i)
        }
        binding.navigationBar.homeButton.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            val i = Intent(this, CategoryActivity::class.java)
            startActivity(i)
        }
        binding.navigationBar.centerIcon.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            val i = Intent(this, ModelActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun resetUI() {
        binding.nameText.text = ""  // Clear name
        binding.profileImage.setImageResource(R.drawable.profile)
        binding.signoutBtn.text=" Sign in "
        binding.signoutBtn.setOnClickListener {  navigateToLogin() }
    }

    private fun loadUserData() {
        val name = sharedPreferences.getString("userName", "User")
        val gender = sharedPreferences.getString("userGender", "female")

        binding.nameText.text = name

        val profileImageRes = if (gender == "male") R.drawable.boy_profile else R.drawable.girl_profile
        binding.profileImage.setImageResource(profileImageRes)
    }

    private fun setupGuestMode() {
        binding.nameText.text = ""  // No username
        binding.profileImage.setImageResource(R.drawable.profile)  // Default image
        binding.signoutBtn.text = "Sign In"  // Change "Sign Out" to "Sign In"
    }

    private fun setupUserProfile() {
        val userName = intent.getStringExtra("USER_NAME")
        if (!userName.isNullOrEmpty()) {
            binding.nameText.text = userName
        }

        val gender = intent.getStringExtra("USER_GENDER")
        if (gender == "Male") {
            binding.profileImage.setImageResource(R.drawable.boy_profile)
        } else {
            binding.profileImage.setImageResource(R.drawable.girl_profile)
        }
    }


    private fun showLoginRequiredMessage() {
        if (isGuest) {
            Toast.makeText(this, "Please log in to use this feature.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
