package com.salmee.artai.data.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(email: String, password: String): Flow<Result<FirebaseUser?>>
    fun signup(email: String, password: String): Flow<Result<FirebaseUser?>>
    fun sendEmailVerification(): Flow<Result<Unit>>
    fun resetPassword(email: String): Flow<Result<Unit>>
    fun getCurrentUser(): FirebaseUser?
    fun logout()
}
