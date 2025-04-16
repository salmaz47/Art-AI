package com.salmee.artai

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Get Views
        val logo = findViewById<ImageView>(R.id.logo)
        val welcomeText = findViewById<TextView>(R.id.welcom_text)
        val worldText = findViewById<TextView>(R.id.world_text)

        // Load Animations
        val fadeInScaleUp = AnimationUtils.loadAnimation(this, R.anim.fade_in_scale_up)

        // Apply Animations
        logo.startAnimation(fadeInScaleUp)

        // Typing Effect for "Welcome To our World"
        typeText(welcomeText, "Welcome To our", 130)
        Handler(Looper.getMainLooper()).postDelayed({
            typeText(worldText, "World", 130)
        }, 1500) // Delay for second text

        // Delay before fading out and switching activity
        Handler(Looper.getMainLooper()).postDelayed({
            val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
            logo.startAnimation(fadeOut)
            welcomeText.startAnimation(fadeOut)
            worldText.startAnimation(fadeOut)

            fadeOut.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    runOnUiThread {
                      startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                       finish()
                    }
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })

        }, 4500) // Transition after animations
    }

    private fun typeText(textView: TextView, text: String, delay: Long) {
        val handler = Handler(Looper.getMainLooper())
        val sb = SpannableStringBuilder()
        text.forEachIndexed { index, char ->
            handler.postDelayed({
                sb.append(char)
                textView.text = sb
            }, index * delay)
        }
    }
}
