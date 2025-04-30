package com.salmee.artai.model // Adjusted package name based on previous file structure

import org.json.JSONObject

// Updated Model for a single image, matching backend GET /images response
data class Image(
    val id: String,       // Corresponds to backend "imageId"
    val imageUrl: String, // Corresponds to backend "imageUrl"
    val prompt: String    // Corresponds to backend "message"
    // isLoved is now managed locally via SharedPreferencesHelper
) {
    companion object {
        // Updated helper function to parse from JSON, matching backend keys
        fun fromJson(json: JSONObject): Image {
            return Image(
                id = json.getString("imageId"),       // Use "imageId" from backend
                imageUrl = json.getString("imageUrl"), // Use "imageUrl" from backend
                prompt = json.getString("message")     // Use "message" from backend for prompt
            )
        }
    }
}

// Model for the image generation request payload
data class ImageGenerateRequest(
    val prompt: String
    // Add other parameters if the backend /generate endpoint requires them
)

// Model for the image generation response (if needed, might just return the new Image object or task ID)
data class ImageGenerateResponse(
    val taskId: String? = null, // Example if it returns an async task ID
    val image: Image? = null    // Example if it returns the created image directly
)

