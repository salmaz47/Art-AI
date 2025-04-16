package com.salmee.artai.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.salmee.artai.ModelActivity
import com.salmee.artai.R
import com.salmee.artai.databinding.ActivityEntertainmentBinding
import com.salmee.artai.utils.animateClickEffect

class EntertainmentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEntertainmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityEntertainmentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Cards Actions
        binding.sportCard.setOnClickListener {
            it.animateClickEffect()
            val i = Intent(this, SportsActivity::class.java)
            startActivity(i)


        }
        binding.shortstoriesCard.setOnClickListener {

            it.animateClickEffect()
            val i = Intent(this, StorysActivity::class.java)
            startActivity(i)
        }
        binding.aroundMeCard.setOnClickListener {
            it.animateClickEffect()
            val i =Intent(this, DrawingActivity::class.java)
            startActivity(i)
        }



        // Nav bar actions
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

        }
    }
}