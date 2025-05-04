package com.salmee.artai.model // Adjusted package name based on previous file structure

import org.json.JSONObject

// Updated Model for a single image, matching backend GET /images response
data class Image(
    val id: String,       // Corresponds to backend "imageId"
    val imageUrl: String, // Corresponds to backend "imageUrl"
    val prompt: String,    // Corresponds to backend "message"
    val isLoved: Boolean = false,
    val isSaved: Boolean = false
) {
    companion object {
        // Updated helper function to parse from JSON, matching backend keys
        fun fromJson(json: JSONObject): Image {
            return Image(
                id = json.optString("imageId", json.optString("id")),       // Use "imageId" from backend
                imageUrl = json.optString("imageUrl", json.optString("image_url")),
                prompt = json.optString("message", json.optString("prompt"))     // Use "message" from backend for prompt
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

