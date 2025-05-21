package com.salmee.artai.ui

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
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
import java.util.Locale

class HabitDetailActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var isFavorite = false
    private lateinit var binding: ActivityHabitDetailBinding
    private lateinit var textToSpeech: TextToSpeech
    private var isSpeaking = false

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityHabitDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize TextToSpeech
        textToSpeech = TextToSpeech(this, this)

        // Get the data from intent
        val name = intent.getStringExtra("habit_name") ?: "Unknown"
        val description = intent.getStringExtra("habit_description") ?: "No description"
        val imageResId = intent.getIntExtra("habit_image", 0)

        // Set the data in UI
        binding.habitTitle.text = name
        binding.habitDescription.text = description
        binding.habitImageView.setImageResource(imageResId)

        // Find and set up speak button
        val speakButton = findViewById<Button>(R.id.speak_btn)
        speakButton.text = getString(R.string.speak)

        // Try to set the icon
        try {
            speakButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.speaker, 0)
        } catch (e: Exception) {
            Log.e("HabitDetailActivity", "Error setting drawable: ${e.message}")
            // Fallback to a standard Android icon
            speakButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_btn_speak_now, 0)
        }

        // Set up speak button click listener
        speakButton.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            toggleSpeech(binding.habitDescription.text.toString())
        }

        // Share button
        binding.shareBtn.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Check out this habit: ${binding.habitTitle.text}\n${binding.habitDescription.text}")
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Share using"))
        }

        // Navigation
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

    // TextToSpeech initialization callback
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Set language to device default
            val result = textToSpeech.setLanguage(Locale.getDefault())

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this,
                    getString(R.string.text_to_speech_language_not_supported), Toast.LENGTH_SHORT).show()
                findViewById<Button>(R.id.speak_btn).isEnabled = false
            }
        } else {
            Toast.makeText(this,
                getString(R.string.text_to_speech_initialization_failed), Toast.LENGTH_SHORT).show()
            findViewById<Button>(R.id.speak_btn).isEnabled = false
        }
    }

    private fun toggleSpeech(text: String) {
        try {
            if (isSpeaking) {
                // If currently speaking, stop it
                if (textToSpeech.isSpeaking) {
                    textToSpeech.stop()
                }
                findViewById<Button>(R.id.speak_btn).text = getString(R.string.speak)
                isSpeaking = false
            } else {
                // Start speaking
                // Using a HashMap for older Android versions compatibility
                val params = HashMap<String, String>()
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "habit_id")
                } else {
                    @Suppress("DEPRECATION")
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params)
                }
                findViewById<Button>(R.id.speak_btn).text = getString(R.string.stop)
                isSpeaking = true
            }
        } catch (e: Exception) {
            Log.e("HabitDetailActivity", "Error in toggleSpeech: ${e.message}")
            Toast.makeText(this,
                getString(R.string.error_with_text_to_speech, e.message), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        // Shutdown TextToSpeech when the activity is destroyed
        if (::textToSpeech.isInitialized) {
            try {
                textToSpeech.stop()
                textToSpeech.shutdown()
            } catch (e: Exception) {
                Log.e("HabitDetailActivity", "Error shutting down TTS: ${e.message}")
            }
        }
        super.onDestroy()
    }
}