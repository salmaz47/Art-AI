package com.salmee.artai.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.salmee.artai.ModelActivity
import com.salmee.artai.R
import java.util.Locale

class StoryDetailActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var textToSpeech: TextToSpeech
    private var isSpeaking = false
    private lateinit var speakButton: Button
    private lateinit var habitDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_detail)

        // Initialize TextToSpeech
        textToSpeech = TextToSpeech(this, this)

        val habitImageView: ImageView = findViewById(R.id.habitImageView)
        val habitTitle: TextView = findViewById(R.id.habitTitle)
        habitDescription = findViewById(R.id.habitDescription)
        speakButton = findViewById(R.id.save_btn)  // Using the save button for speak functionality
        val shareButton: Button = findViewById(R.id.share_btn)
        val profileButton: ImageView = findViewById(R.id.profileButton)
        val homeButton: ImageView = findViewById(R.id.homeButton)
        val centerButton: ImageView = findViewById(R.id.centerIcon)

        // Get the data from intent
        val name = intent.getStringExtra("habit_name") ?: "Unknown"
        val description = intent.getStringExtra("habit_description") ?: "No description"
        val imageResId = intent.getIntExtra("habit_image", 0)
        val titleColor = intent.getIntExtra("habit_color", Color.BLACK)

        habitDescription.movementMethod = ScrollingMovementMethod()

        // Set the data in UI
        habitTitle.text = name
        habitDescription.text = description
        habitImageView.setImageResource(imageResId)
        habitTitle.setBackgroundColor(titleColor)

        // Change the button text to "Speak"
        speakButton.text = getString(R.string.speak)
        speakButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.speaker, 0)  // You might want to change this icon

        // handling buttons
        speakButton.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            toggleSpeech()
        }

        shareButton.setOnClickListener {
            val shareText = "Check out this story:\n${habitTitle.text}\n${habitDescription.text}"

            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }

            // Ask the system whether anyone can handle the intent
            if (sendIntent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(sendIntent, "Share via"))
            } else {
                Toast.makeText(this,
                    getString(R.string.no_app_installed_to_share_text), Toast.LENGTH_SHORT).show()
            }
        }

        profileButton.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            val i = Intent(this, ProfileActivity::class.java)
            startActivity(i)
        }

        homeButton.setOnClickListener {
            it.startAnimation(AnimationUtils.loadAnimation(this, R.anim.button_elevation))
            val i = Intent(this, CategoryActivity::class.java)
            startActivity(i)
        }

        centerButton.setOnClickListener {
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
                Toast.makeText(this, getString(R.string.text_to_speech_language_not_supported), Toast.LENGTH_SHORT).show()
                speakButton.isEnabled = false
            }
        } else {
            Toast.makeText(this, getString(R.string.text_to_speech_initialization_failed), Toast.LENGTH_SHORT).show()
            speakButton.isEnabled = false
        }
    }

    private fun toggleSpeech() {
        if (isSpeaking) {
            // If currently speaking, stop it
            if (textToSpeech.isSpeaking) {
                textToSpeech.stop()
            }
            speakButton.text = getString(R.string.speak)
            isSpeaking = false
        } else {
            // Start speaking
            val text = habitDescription.text.toString()
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "story_id")
            speakButton.text = getString(R.string.stop)
            isSpeaking = true
        }
    }

    override fun onDestroy() {
        // Shutdown TextToSpeech when the activity is destroyed
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }
}