package com.salmee.artai

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.salmee.artai.databinding.ActivityModelBinding
import com.salmee.artai.ui.CategoryActivity
import com.salmee.artai.ui.ProfileActivity
import com.salmee.artai.core.Constants.Api
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import com.bumptech.glide.Glide
import android.widget.ImageView
// import com.bumptech.glide.Glide


class ModelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModelBinding
    private var isApiResponseReceived = false // Track API response
    private val handler = Handler(Looper.getMainLooper()) // Handler for animations

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityModelBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Navigation buttons
        binding.navigationBar.profileButton.setOnClickListener {
            val i = Intent(this, ProfileActivity::class.java)
            startActivity(i)
        }
        binding.navigationBar.homeButton.setOnClickListener {
            val i = Intent(this, CategoryActivity::class.java)
            startActivity(i)
        }
        binding.navigationBar.centerIcon.setOnClickListener {
            val i = Intent(this, ModelActivity::class.java)
            startActivity(i)
            finish()
        }

        // Car animation when clicking the car button inside EditText
        binding.carButton.setOnClickListener {
            isApiResponseReceived = false // Reset API response flag
            startCarAnimation()
//            simulateApiCall() // Replace this with your actual API call
            callGenerateApi()
        }
    }

    private fun startCarAnimation() {
        val startX = binding.movingCar.x
        val endX = startX + 1300 // Move right by 50 pixels

        var count = 0 // Loop counter

        fun animateCar() {
            if (isApiResponseReceived||count>=3) {
                binding.movingCar.visibility = View.GONE // Hide car when API response arrives
                return
            }

            val animation = TranslateAnimation(0f, endX - startX, 0f, 0f)
            animation.duration = 700 // 0.5 seconds
            animation.repeatCount = 0 // Move back and forth
            animation.repeatMode = TranslateAnimation.REVERSE
            animation.fillAfter = true

            animation.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
                override fun onAnimationStart(animation: android.view.animation.Animation?) {}

                override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                    count++
                    if (!isApiResponseReceived && count < 3) {
                        handler.postDelayed({ animateCar() }, 100) // Restart animation if needed
                    }
                }

                override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}
            })

            binding.movingCar.startAnimation(animation)
        }

        binding.movingCar.visibility = View.VISIBLE
        animateCar()
    }

    private fun simulateApiCall() {
        
        // Simulate API response after 3 seconds
        handler.postDelayed({
            isApiResponseReceived = true
            binding.movingCar.visibility = View.GONE
           // binding.generatedImage.setBackgroundResource(R.drawable.generated_image) // Example update
        }, 3000)
    }
    private fun callGenerateApi() {
        val client = OkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val promptText = binding.inputText.text.toString()

        // Get the token from SharedPreferences
        val token = getSharedPreferences("MyPrefs", MODE_PRIVATE).getString("token", "")
    
        Log.d("ModelActivity", "Generate API call")
    
        val jsonBody = JSONObject().apply {
            put("prompt", promptText)
        }
    
        val request = Request.Builder()
            .url(Api.GENERTE_IMAGE)
            .addHeader("Authorization", "Bearer $token") // Add token to headers
            .post(jsonBody.toString().toRequestBody(mediaType))
            .build()
    
        Thread {
            try {
                client.newCall(request).execute().use { response ->
                    val responseBody = response.body?.string()
                    Log.d("ModelActivity", "Response: $responseBody")
                    val jsonResponse = JSONObject(responseBody ?: "")
                    val imageUrl = jsonResponse.getString("imageUrl") // Assuming this is a valid URL
                
                    if (response.isSuccessful) {
                        isApiResponseReceived = true
                        Log.d("ModelActivity", "Image URL: $imageUrl")

                        runOnUiThread {
                            // Use Glide to load the image into the ImageView
                            Glide.with(this)
    .load(imageUrl)
    .into(binding.generatedImageView)  // Use the correct ID here
                        }
                    } else {
                        Log.e("ModelActivity", "Failed to fetch image URL from API response")
                    }
                }
            } catch (e: Exception) {
                Log.e("ModelActivity", "API call failed: ${e.message}")
            }
        }.start()
    }
}
