package com.salmee.artai.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.salmee.artai.ModelActivity
import com.salmee.artai.R
import com.salmee.artai.databinding.ActivityHabitDetailBinding

class HabitDetailActivity : AppCompatActivity() {
    private var isFavorite = false
    private lateinit var binding: ActivityHabitDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding= ActivityHabitDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val habitImageView: ImageView = findViewById(R.id.habitImageView)
        val habitTitle: TextView = findViewById(R.id.habitTitle)
        val habitDescription: TextView = findViewById(R.id.habitDescription)
        val shareButton: Button = findViewById(R.id.share_btn)
        val favButton : Button = findViewById(R.id.fav_btn)

        // Get the data from intent
        val name = intent.getStringExtra("habit_name") ?: "Unknown"
        val description = intent.getStringExtra("habit_description") ?: "No description"
        val imageResId = intent.getIntExtra("habit_image", 0)

        // Set the data in UI
        habitTitle.text = name
        habitDescription.text = description
        habitImageView.setImageResource(imageResId)

        favButton.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            isFavorite = !isFavorite  // Toggle the state

        }



        shareButton.setOnClickListener {
            val shareText = "Check out this habit:\n${habitTitle.text}\n${habitDescription.text}"

            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }

            // ❯ Ask the system whether anyone can handle the intent
            if (sendIntent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(sendIntent, "Share via"))
            } else {
                Toast.makeText(this, "No app installed to share text.", Toast.LENGTH_SHORT).show()
            }
        }

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
}
