package com.salmee.artai.data.repository

import com.salmee.artai.model.Image
import com.salmee.artai.model.ImageGenerateRequest
import com.salmee.artai.model.ImageGenerateResponse
import com.salmee.artai.model.User
import kotlinx.coroutines.flow.Flow

// Define ImageFilter enum with a string value for API calls
enum class ImageFilter(val value: String) {
    ALL("all"), // Example filter value, adjust if backend expects something different
    // MY_IMAGES("my_images"), // If backend supports filtering by user
    // FAVORITES("favorites") // If backend supports filtering favorites
}

interface ImageRepository {
    fun getImages(filter: ImageFilter): Flow<Result<List<Image>>>
    fun getImage(imageId: String): Flow<Result<Image>>
    fun deleteImage(imageId: String): Flow<Result<Unit>>
    fun loveImage(imageId: String): Flow<Result<Boolean>> // Returns new love status
    fun generateImage(request: ImageGenerateRequest): Flow<Result<ImageGenerateResponse>>
}

