package com.salmee.artai.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salmee.artai.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import com.salmee.artai.core.Constants.Api

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val client = OkHttpClient()
    private val mediaType = "application/json; charset=utf-8".toMediaType()
    private val baseUrl = "https://3a89-156-193-239-189.ngrok-free.app/api"

    //! I know that the logic should not be in this file, but I'm not a Kotlin expert and it causes some errors that I'm not intrested in fixing.
    //! So the logic in login, signup functions should be moved to AuthRepositoryImpl.kt file.
    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        
        // authRepository.login(email, password)
        //     .onEach { result ->
        //         result.fold(
        //             onSuccess = { onResult(true, null) },
        //             onFailure = { onResult(false, it.message) }
        //         )
        //     }
        //     .launchIn(viewModelScope)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val jsonBody = JSONObject().apply {
                    
                    put("email", email)
                    put("password", password)
                }

                val request = Request.Builder()
                    .url(Api.LOGIN_ENDPOINT)
                    .post(jsonBody.toString().toRequestBody(mediaType))
                    .build()
                    Log.d("AuthViewModel", "Sending LOGIN Request")

                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        Log.d("AuthViewModel", "Login successful")
                        val responseBody = response.body?.string()
                        val jsonResponse = JSONObject(responseBody)
                        val token = jsonResponse.getString("token")
                        withContext(Dispatchers.Main) {
                            onResult(true, null)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            val errorBody = response.body?.string()
                            val errorJson = JSONObject(errorBody ?: "{}")
                            val errorMessage = errorJson.optString("message", "Login failed")
                            Log.d("AuthViewModel", "Login Failed: $errorMessage")
                            onResult(false, errorMessage)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Login error: ${e.message}")
                withContext(Dispatchers.Main) {
                    onResult(false, e.message)
                }
            }
        }
        
    }

    

    fun signup(name: String, email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        // authRepository.signup(name,email, password)
        //     .onEach { result ->
        //         result.fold(
        //             onSuccess = { onResult(true, null) },
        //             onFailure = { onResult(false, it.message) }
        //         )
        //     }
        //     .launchIn(viewModelScope)

        
        //! NOTE: MAKE SURE TO CHECK IF THE EMULATOR HAVE INTERNET CONNECTION.
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val jsonBody = JSONObject().apply {
                    put("name", name)
                    put("email", email)
                    put("password", password)
                }


                val request = Request.Builder()
                    .url(Api.SIGNUP_ENDPOINT)
                    .post(jsonBody.toString().toRequestBody(mediaType))
                    .build()
                    Log.d("AuthViewModel", "Sending SIGNUP Request")

                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        Log.d("AuthViewModel", "Signup successful")
                        val responseBody = response.body?.string()
                        val jsonResponse = JSONObject(responseBody)
                        val token = jsonResponse.getString("token")
                        withContext(Dispatchers.Main) {
                            onResult(true, null)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            val errorBody = response.body?.string()
                            val errorJson = JSONObject(errorBody ?: "{}")
                            val errorMessage = errorJson.optString("message", "Registration failed")
                            Log.d("AuthViewModel", "Signup Failed: $errorMessage")
                            onResult(false, errorMessage)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Signup error: ${e.message}")
                withContext(Dispatchers.Main) {
                    onResult(false, e.message)
                }
            }
        }
    }



    fun sendPasswordReset(email: String, onResult: (Boolean, String?) -> Unit) {
        authRepository.resetPassword(email)
            .onEach { result ->
                result.fold(
                    onSuccess = { onResult(true, null) },
                    onFailure = { onResult(false, it.message) }
                )
            }
            .launchIn(viewModelScope)
    }

    fun sendEmailVerification(onResult: (Boolean, String?) -> Unit) {
        authRepository.sendEmailVerification()
            .onEach { result ->
                result.fold(
                    onSuccess = { onResult(true, null) },
                    onFailure = { onResult(false, it.message) }
                )
            }
            .launchIn(viewModelScope)
    }

    fun logout() {
        authRepository.logout()
    }

    fun getCurrentUser(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }
}
