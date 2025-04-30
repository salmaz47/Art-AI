package com.salmee.artai.data.repository

import SharedPreferencesHelper
import android.content.Context
import android.util.Log
import com.salmee.artai.core.Constants
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AuthRepositoryImpl(private val context: Context) : AuthRepository {
    // Use the base URL from the original file, ensure it's correct
    private val baseUrl = "${Constants.BASE_URL}/api/auth" // Adjusted to include /auth
    private val client = OkHttpClient()
    private val mediaType = "application/json; charset=utf-8".toMediaType()
    private val prefsHelper = SharedPreferencesHelper // Access object directly

    override fun signup(name: String, email: String, password: String): Flow<Result<Boolean>> = callbackFlow {
        try {
            Log.d("AuthRepositoryImpl", "Attempting signup for: $email")
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
                        if (responseBody != null) {
                            val jsonResponse = JSONObject(responseBody)
                            val token = jsonResponse.optString("token") // Use optString for safety
                            if (token.isNotEmpty()) {
                                prefsHelper.saveAuthToken(context, token)
                                prefsHelper.setGuestMode(context, false) // Ensure not guest after signup
                                Log.i("AuthRepositoryImpl", "Signup successful, token saved.")
                                trySend(Result.success(true))
                            } else {
                                Log.w("AuthRepositoryImpl", "Signup successful but no token received.")
                                trySend(Result.failure(Exception("Signup successful but no token received.")))
                            }
                        } else {
                            Log.w("AuthRepositoryImpl", "Signup successful but empty response body.")
                            trySend(Result.failure(Exception("Signup successful but empty response body.")))
                        }
                    } else {
                        val errorBody = response.body?.string()
                        Log.e("AuthRepositoryImpl", "Signup failed: ${response.code}, Body: $errorBody")
                        trySend(Result.failure(Exception("Signup failed: ${response.code} - ${errorBody ?: "Unknown error"}")))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Signup error: ${e.message}", e)
            trySend(Result.failure(e))
        }
        awaitClose { Log.d("AuthRepositoryImpl", "Signup Flow closed") }
    }

    override fun login(email: String, password: String): Flow<Result<Boolean>> = callbackFlow {
        try {
            Log.d("AuthRepositoryImpl", "Attempting login for: $email")
            val jsonBody = JSONObject().apply {
                put("email", email)
                put("password", password)
            }

            val request = Request.Builder()
                .url("$baseUrl/login")
                .post(jsonBody.toString().toRequestBody(mediaType))
                .build()

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        if (responseBody != null) {
                            val jsonResponse = JSONObject(responseBody)
                            val token = jsonResponse.optString("token")
                            if (token.isNotEmpty()) {
                                prefsHelper.saveAuthToken(context, token)
                                prefsHelper.setGuestMode(context, false) // Ensure not guest after login
                                Log.i("AuthRepositoryImpl", "Login successful, token saved.")
                                trySend(Result.success(true))
                            } else {
                                Log.w("AuthRepositoryImpl", "Login successful but no token received.")
                                trySend(Result.failure(Exception("Login successful but no token received.")))
                            }
                        } else {
                            Log.w("AuthRepositoryImpl", "Login successful but empty response body.")
                            trySend(Result.failure(Exception("Login successful but empty response body.")))
                        }
                    } else {
                        val errorBody = response.body?.string()
                        Log.e("AuthRepositoryImpl", "Login failed: ${response.code}, Body: $errorBody")
                        trySend(Result.failure(Exception("Login failed: ${response.code} - ${errorBody ?: "Unknown error"}")))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Login error: ${e.message}", e)
            trySend(Result.failure(e))
        }
        awaitClose { Log.d("AuthRepositoryImpl", "Login Flow closed") }
    }

    // Implement reset password using backend endpoint
    override fun resetPassword(email: String): Flow<Result<Unit>> = callbackFlow {
        try {
            Log.d("AuthRepositoryImpl", "Attempting password reset for: $email")
            val jsonBody = JSONObject().apply {
                put("email", email)
            }

            val request = Request.Builder()
                .url("$baseUrl/reset-password") // Assuming this is the correct endpoint
                .post(jsonBody.toString().toRequestBody(mediaType))
                .build()

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        Log.i("AuthRepositoryImpl", "Password reset email request successful for $email.")
                        trySend(Result.success(Unit))
                    } else {
                        val errorBody = response.body?.string()
                        Log.e("AuthRepositoryImpl", "Password reset failed: ${response.code}, Body: $errorBody")
                        trySend(Result.failure(Exception("Password reset failed: ${response.code} - ${errorBody ?: "Unknown error"}")))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Password reset error: ${e.message}", e)
            trySend(Result.failure(e))
        }
        awaitClose { Log.d("AuthRepositoryImpl", "Reset Password Flow closed") }
    }

    // No direct Firebase user needed. Check token presence instead.
    // fun getCurrentUser(): Any? = if (prefsHelper.getAuthToken(context) != null) true else null

    override fun logout() {
        Log.d("AuthRepositoryImpl", "Logging out.")
        prefsHelper.clearAuthToken(context)
        // Optionally set guest mode on logout, depending on desired app flow
        // prefsHelper.setGuestMode(context, true)
    }
}

