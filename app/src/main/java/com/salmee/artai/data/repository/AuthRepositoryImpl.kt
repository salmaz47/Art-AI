package com.salmee.artai.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject
import java.io.OutputStreamWriter
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(private val firebaseAuth: FirebaseAuth) : AuthRepository {
    private val baseUrl = "https://3a89-156-193-239-189.ngrok-free.app/api"  
    private val client = OkHttpClient()
        private val mediaType = "application/json; charset=utf-8".toMediaType()
    
        override fun signup(name: String, email: String, password: String): Flow<Result<FirebaseUser?>> = callbackFlow {
            try {
                Log.d("AuthRepository", "Sending request w")

                val jsonBody = JSONObject().apply {
                    put("name", name)
                    put("email", email)
                    put("password", password)
                }
    
                val request = Request.Builder()
                    .url("$baseUrl/signup")
                    .post(jsonBody.toString().toRequestBody(mediaType))
                    .build()
    
                withContext(Dispatchers.IO) {
                    client.newCall(request).execute().use { response ->
                        if (response.isSuccessful) {
                            val responseBody = response.body?.string()
                            val jsonResponse = JSONObject(responseBody)
                            val token = jsonResponse.getString("token")
                            Log.d("AuthRepository", "Sending request")
                            
                            trySend(Result.success(null))
                        } else {
                            Log.d("AuthRepository", "Error response: ${response.code}")
                            trySend(Result.failure(Exception("Backend registration failed with code: ${response.code}")))
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("AuthRepository", "Signup error: ${e.message}", e)
                trySend(Result.failure(e))
            }
            awaitClose()
        }

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
