package com.salmee.artai.model // Adjusted package name based on previous file structure

import org.json.JSONObject

// Model for a single image, matching backend GET /images/<image_id> response
data class Image(
    val id: String, // Assuming ID is String (UUID) or Int
    val imageUrl: String,
    val prompt: String,
    val isLoved: Boolean, // From UserImage join table
    val isSaved: Boolean  // From UserImage join table
) {
    companion object {
        // Helper function to parse from JSON, assuming backend returns JSON objects for images
        fun fromJson(json: JSONObject): Image {
            return Image(
                id = json.getString("imageId"), // Adjust key if needed
                imageUrl = json.getString("imageUrl"),
                prompt = json.getString("message"),
                isLoved = json.optBoolean("is_loved", false), // Use optBoolean with default
                isSaved = json.optBoolean("is_saved", false)  // Use optBoolean with default
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

