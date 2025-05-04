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
import com.salmee.artai.model.Image

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

//        favButton.setOnClickListener {
//            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
//
//            val image = Image(
//                id = imageResId,
//                imageUrl =
//            )
//
//            isFavorite = SharedPreferencesHelper.toggleFavorite(this, image)
//            Toast.makeText(this, if (isFavorite) "Added to favorites" else "Removed from favorites", Toast.LENGTH_SHORT).show()
//        }




        shareButton.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Check out this habit: ${habitTitle.text}\n${habitDescription.text}")
                type = "text/plain"
            }

            startActivity(Intent.createChooser(shareIntent, "Share using"))
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
