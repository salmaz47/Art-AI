package com.salmee.artai.data.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

// Interface remains the same for now, but return types might change from FirebaseUser
interface AuthRepository {
    fun signup(name: String, email: String, password: String): Flow<Result<Boolean>> // Return Boolean for success
    fun login(email: String, password: String): Flow<Result<Boolean>> // Return Boolean for success
    fun resetPassword(email: String): Flow<Result<Unit>>
    // fun getCurrentUser(): Any? // Decide how to represent logged-in state - maybe just check token?
    fun logout()
}
