package com.salmee.artai.model // Adjusted package name based on previous file structure

// Updated User model to better match backend /user/profile response
data class User(
    val id: String, // Assuming ID is a string (like UUID) or Int, adjust if needed
    val name: String,
    val email: String,
    val avatarUrl: String?, // Nullable, maps to avatar_url
    val role: String // Maps to role enum ('user', 'admin')
)

// Separate data class for profile updates, as not all fields might be updatable at once
data class UserProfileUpdate(
    val name: String? = null,
    val avatarUrl: String? = null // Or potentially Base64 string if backend supports it
)

// Data class for password change
data class UserPasswordUpdate(
    val currentPassword: String,
    val newPassword: String
)

