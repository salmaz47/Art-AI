package com.salmee.artai.utils

import android.content.Context // Import Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salmee.artai.data.repository.AuthRepository
import com.salmee.artai.data.repository.AuthRepositoryImpl
import com.salmee.artai.data.repository.ImageRepository
import com.salmee.artai.data.repository.ImageRepositoryImpl
import com.salmee.artai.data.repository.UserRepository
import com.salmee.artai.data.repository.UserRepositoryImpl
import com.salmee.artai.presentation.viewmodel.AuthViewModel
import com.salmee.artai.presentation.viewmodel.ImageViewModel
import com.salmee.artai.presentation.viewmodel.UserViewModel

// Updated ViewModelFactory to handle different ViewModel types and dependencies
class ViewModelFactory(
    private val context: Context, // Pass application context
    private val authRepository: AuthRepository? = null,
    private val userRepository: UserRepository? = null,
    private val imageRepository: ImageRepository? = null
) : ViewModelProvider.Factory {

    // Lazy initialization of repositories if not provided
    private val defaultAuthRepository: AuthRepository by lazy {
        AuthRepositoryImpl(context)
    }
    private val defaultUserRepository: UserRepository by lazy {
        UserRepositoryImpl(context)
    }
    private val defaultImageRepository: ImageRepository by lazy {
        ImageRepositoryImpl(context)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(authRepository ?: defaultAuthRepository) as T
            }
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                UserViewModel(userRepository ?: defaultUserRepository, authRepository ?: defaultAuthRepository) as T
            }
            modelClass.isAssignableFrom(ImageViewModel::class.java) -> {
                // ImageViewModel now requires context
                ImageViewModel(imageRepository ?: defaultImageRepository, context) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

