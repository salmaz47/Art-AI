package com.salmee.artai.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.salmee.artai.ModelActivity
import com.salmee.artai.R
import com.salmee.artai.databinding.ActivityEducationBinding
import com.salmee.artai.utils.animateClickEffect

class EducationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEducationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityEducationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Cards Actions
        binding.numbersCard.setOnClickListener {
            it.animateClickEffect()
            val i = Intent(this, NumbersActivity::class.java)
            startActivity(i)


        }
        binding.lettersCard.setOnClickListener {

            it.animateClickEffect()
            val i = Intent(this, LettersActivity::class.java)
            startActivity(i)
        }
        binding.shapesCard.setOnClickListener {
            it.animateClickEffect()
            val i =Intent(this, ShapesActivity::class.java)
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