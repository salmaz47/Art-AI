package com.salmee.artai.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(private val firebaseAuth: FirebaseAuth) : AuthRepository {

    override fun login(email: String, password: String): Flow<Result<FirebaseUser?>> = callbackFlow {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(Result.success(firebaseAuth.currentUser))
                } else {
                    trySend(Result.failure(task.exception ?: Exception("Login failed")))
                }
            }
        awaitClose()
    }


    override fun signup(email: String, password: String): Flow<Result<FirebaseUser?>> = callbackFlow {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(Result.success(firebaseAuth.currentUser))
                } else {
                    trySend(Result.failure(task.exception ?: Exception("Signup failed")))
                }
            }
        awaitClose()
    }



    override fun sendEmailVerification(): Flow<Result<Unit>> = callbackFlow {
        val user = firebaseAuth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                trySend(Result.success(Unit))
            } else {
                trySend(Result.failure(task.exception ?: Exception("Failed to send verification email")))
            }
        }
        awaitClose()
    }

    override fun resetPassword(email: String): Flow<Result<Unit>> = callbackFlow {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(Result.success(Unit))
                } else {
                    trySend(Result.failure(task.exception ?: Exception("Failed to reset password")))
                }
            }
        awaitClose()
    }

    override fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    override fun logout() {
        firebaseAuth.signOut()
    }
}
