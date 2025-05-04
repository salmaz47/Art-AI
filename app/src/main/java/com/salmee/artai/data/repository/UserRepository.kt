package com.salmee.artai.data.repository

import com.salmee.artai.model.User
import com.salmee.artai.model.UserProfileUpdate
import com.salmee.artai.model.UserPasswordUpdate
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getProfile(): Flow<Result<User>>
    fun updateProfile(profileUpdate: UserProfileUpdate): Flow<Result<User>> // Return updated user
    fun updatePassword(passwordUpdate: UserPasswordUpdate): Flow<Result<Unit>>
    fun deleteAccount(): Flow<Result<Unit>>
}

