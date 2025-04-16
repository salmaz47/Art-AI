package com.salmee.artai.ui

import android.view.View
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.salmee.artai.utils.animateClickEffect
import androidx.appcompat.app.AppCompatActivity
import com.salmee.artai.ModelActivity
import com.salmee.artai.R
import com.salmee.artai.databinding.ActivityCategoryBinding


class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Cards Actions
        binding.lifeStyleCard.setOnClickListener {
            it.animateClickEffect()
            val i = Intent(this, LifestyleActivity::class.java)
            startActivity(i)
        }
        binding.eductionCard.setOnClickListener {
            it.animate()
                .scaleX(1.1f)  // Scale up
                .scaleY(1.1f)
                .setDuration(100)
                .withEndAction {
                    it.animate()
                        .scaleX(1f)  // Scale back to normal
                        .scaleY(1f)
                        .setDuration(100)
                }
            val i = Intent(this, EducationActivity::class.java)
            startActivity(i)

        }
        binding.entertainmentCard.setOnClickListener {
            it.animate()
                .scaleX(1.1f)  // Scale up
                .scaleY(1.1f)
                .setDuration(100)
                .withEndAction {
                    it.animate()
                        .scaleX(1f)  // Scale back to normal
                        .scaleY(1f)
                        .setDuration(100)
                }

            val i = Intent(this, EntertainmentActivity::class.java)
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
            finish()
        }
    }
}

