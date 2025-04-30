package com.salmee.artai.data.repository

import SharedPreferencesHelper
import android.content.Context
import android.util.Log
import com.salmee.artai.model.User
import com.salmee.artai.model.UserProfileUpdate
import com.salmee.artai.model.UserPasswordUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class UserRepositoryImpl(private val context: Context) : UserRepository {

    private val baseUrl = "https://3a89-156-193-239-189.ngrok-free.app/api/user" // Base URL for user endpoints
    private val client = OkHttpClient()
    private val mediaType = "application/json; charset=utf-8".toMediaType()
    private val prefsHelper = SharedPreferencesHelper

    // Helper to add auth token to requests
    private fun createAuthenticatedRequest(url: String, method: String = "GET", body: JSONObject? = null): Request {
        val token = prefsHelper.getAuthToken(context)
        if (token == null) {
            Log.w("UserRepositoryImpl", "Auth token is null. Cannot make authenticated request to $url")
            // Or throw an exception, depending on how you want to handle this
            throw IllegalStateException("User not authenticated")
        }

        val requestBody = body?.toString()?.toRequestBody(mediaType)
        val builder = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $token") // Add JWT token

        when (method.uppercase()) {
            "GET" -> builder.get()
            "PUT" -> builder.put(requestBody ?: "{}".toRequestBody(mediaType)) // Empty body if null
            "POST" -> builder.post(requestBody ?: "{}".toRequestBody(mediaType))
            "DELETE" -> builder.delete(requestBody) // DELETE can optionally have a body
            else -> throw IllegalArgumentException("Unsupported HTTP method: $method")
        }
        return builder.build()
    }

    override fun getProfile(): Flow<Result<User>> = callbackFlow {
        try {
            if (prefsHelper.isGuestMode(context)) {
                 trySend(Result.failure(Exception("Cannot fetch profile in guest mode.")))
                 awaitClose()
                 return@callbackFlow
            }

            Log.d("UserRepositoryImpl", "Fetching user profile")
            val request = createAuthenticatedRequest("$baseUrl/profile")

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        if (responseBody != null) {
                            val json = JSONObject(responseBody)
                            // Assuming the backend returns the user object directly
                            val user = User(
                                id = json.getString("id"), // Adjust keys based on actual backend response
                                name = json.getString("name"),
                                email = json.getString("email"),
                                avatarUrl = json.optString("avatar_url", null),
                                role = json.getString("role")
                            )
                            Log.i("UserRepositoryImpl", "Profile fetched successfully.")
                            trySend(Result.success(user))
                        } else {
                            Log.w("UserRepositoryImpl", "Get profile successful but empty response body.")
                            trySend(Result.failure(Exception("Get profile successful but empty response body.")))
                        }
                    } else {
                        val errorBody = response.body?.string()
                        Log.e("UserRepositoryImpl", "Get profile failed: ${response.code}, Body: $errorBody")
                        trySend(Result.failure(Exception("Get profile failed: ${response.code} - ${errorBody ?: "Unknown error"}")))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "Get profile error: ${e.message}", e)
            trySend(Result.failure(e))
        }
        awaitClose { Log.d("UserRepositoryImpl", "Get Profile Flow closed") }
    }

    override fun updateProfile(profileUpdate: UserProfileUpdate): Flow<Result<User>> = callbackFlow {
         try {
             if (prefsHelper.isGuestMode(context)) {
                 trySend(Result.failure(Exception("Cannot update profile in guest mode.")))
                 awaitClose()
                 return@callbackFlow
            }

            Log.d("UserRepositoryImpl", "Updating user profile")
            val jsonBody = JSONObject().apply {
                profileUpdate.name?.let { put("name", it) }
                profileUpdate.avatarUrl?.let { put("avatar_url", it) } // Key might be just "avatar"
            }

            val request = createAuthenticatedRequest("$baseUrl/profile", method = "PUT", body = jsonBody)

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                         val responseBody = response.body?.string()
                        if (responseBody != null) {
                            val json = JSONObject(responseBody)
                            // Assuming backend returns the updated user object
                             val user = User(
                                id = json.getString("id"),
                                name = json.getString("name"),
                                email = json.getString("email"),
                                avatarUrl = json.optString("avatar_url", null),
                                role = json.getString("role")
                            )
                            Log.i("UserRepositoryImpl", "Profile updated successfully.")
                            trySend(Result.success(user))
                        } else {
                             Log.w("UserRepositoryImpl", "Update profile successful but empty response body.")
                            trySend(Result.failure(Exception("Update profile successful but empty response body.")))
                        }
                    } else {
                        val errorBody = response.body?.string()
                        Log.e("UserRepositoryImpl", "Update profile failed: ${response.code}, Body: $errorBody")
                        trySend(Result.failure(Exception("Update profile failed: ${response.code} - ${errorBody ?: "Unknown error"}")))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "Update profile error: ${e.message}", e)
            trySend(Result.failure(e))
        }
        awaitClose { Log.d("UserRepositoryImpl", "Update Profile Flow closed") }
    }

    override fun updatePassword(passwordUpdate: UserPasswordUpdate): Flow<Result<Unit>> = callbackFlow {
         try {
             if (prefsHelper.isGuestMode(context)) {
                 trySend(Result.failure(Exception("Cannot update password in guest mode.")))
                 awaitClose()
                 return@callbackFlow
            }

            Log.d("UserRepositoryImpl", "Updating user password")
            val jsonBody = JSONObject().apply {
                put("current_password", passwordUpdate.currentPassword) // Adjust keys if needed
                put("new_password", passwordUpdate.newPassword)
            }

            val request = createAuthenticatedRequest("$baseUrl/password", method = "PUT", body = jsonBody)

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        Log.i("UserRepositoryImpl", "Password updated successfully.")
                        trySend(Result.success(Unit))
                    } else {
                        val errorBody = response.body?.string()
                        Log.e("UserRepositoryImpl", "Update password failed: ${response.code}, Body: $errorBody")
                        trySend(Result.failure(Exception("Update password failed: ${response.code} - ${errorBody ?: "Unknown error"}")))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "Update password error: ${e.message}", e)
            trySend(Result.failure(e))
        }
        awaitClose { Log.d("UserRepositoryImpl", "Update Password Flow closed") }
    }

    override fun deleteAccount(): Flow<Result<Unit>> = callbackFlow {
         try {
             if (prefsHelper.isGuestMode(context)) {
                 trySend(Result.failure(Exception("Cannot delete account in guest mode.")))
                 awaitClose()
                 return@callbackFlow
            }

            Log.d("UserRepositoryImpl", "Deleting user account")
            val request = createAuthenticatedRequest("$baseUrl/account", method = "DELETE")

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        Log.i("UserRepositoryImpl", "Account deleted successfully.")
                        prefsHelper.clearAll(context) // Clear prefs after successful deletion
                        trySend(Result.success(Unit))
                    } else {
                        val errorBody = response.body?.string()
                        Log.e("UserRepositoryImpl", "Delete account failed: ${response.code}, Body: $errorBody")
                        trySend(Result.failure(Exception("Delete account failed: ${response.code} - ${errorBody ?: "Unknown error"}")))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "Delete account error: ${e.message}", e)
            trySend(Result.failure(e))
        }
        awaitClose { Log.d("UserRepositoryImpl", "Delete Account Flow closed") }
    }
}

