package com.salmee.artai.data.repository

import SharedPreferencesHelper
import android.content.Context
import android.util.Log
import com.salmee.artai.model.Image
import com.salmee.artai.model.ImageGenerateRequest
import com.salmee.artai.model.ImageGenerateResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class ImageRepositoryImpl(private val context: Context) : ImageRepository {

    private val baseUrl = "https://3a89-156-193-239-189.ngrok-free.app/api/images" // Base URL for images endpoints
    private val client = OkHttpClient()
    private val mediaType = "application/json; charset=utf-8".toMediaType()
    private val prefsHelper = SharedPreferencesHelper

    // Reusing the helper from UserRepositoryImpl would be better in a real app (e.g., via a common NetworkClient or interceptor)
    private fun createAuthenticatedRequest(url: String, method: String = "GET", body: JSONObject? = null): Request {
        val token = prefsHelper.getAuthToken(context)
        if (token == null && !prefsHelper.isGuestMode(context)) {
             // Only throw if not guest and token is missing. Guests shouldn't reach here for authed routes.
            Log.w("ImageRepositoryImpl", "Auth token is null for non-guest user. Cannot make authenticated request to $url")
            throw IllegalStateException("User not authenticated")
        } else if (token == null && prefsHelper.isGuestMode(context)){
             Log.w("ImageRepositoryImpl", "Attempting authenticated request in guest mode to $url")
             throw IllegalStateException("Cannot perform this action in guest mode")
        }

        val requestBody = body?.toString()?.toRequestBody(mediaType)
        val builder = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $token") // Add JWT token

        when (method.uppercase()) {
            "GET" -> builder.get()
            "PUT" -> builder.put(requestBody ?: "{}".toRequestBody(mediaType))
            "POST" -> builder.post(requestBody ?: "{}".toRequestBody(mediaType))
            "DELETE" -> builder.delete(requestBody)
            else -> throw IllegalArgumentException("Unsupported HTTP method: $method")
        }
        return builder.build()
    }

    override fun getImages(filter: ImageFilter): Flow<Result<List<Image>>> = callbackFlow {
        try {
             if (prefsHelper.isGuestMode(context)) {
                 // Decide guest behavior: empty list or error? Let's return empty list for viewing.
                 Log.i("ImageRepositoryImpl", "Guest mode: Returning empty image list.")
                 trySend(Result.success(emptyList()))
                 awaitClose()
                 return@callbackFlow
            }

            Log.d("ImageRepositoryImpl", "Fetching images with filter: ${filter.value}")
            val request = createAuthenticatedRequest("$baseUrl?filter=${filter.value}")

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        if (responseBody != null) {
                            val jsonArray = JSONArray(responseBody) // Assuming backend returns a JSON array
                            val images = mutableListOf<Image>()
                            for (i in 0 until jsonArray.length()) {
                                images.add(Image.fromJson(jsonArray.getJSONObject(i)))
                            }
                            Log.i("ImageRepositoryImpl", "Fetched ${images.size} images.")
                            trySend(Result.success(images))
                        } else {
                            Log.w("ImageRepositoryImpl", "Get images successful but empty response body.")
                            trySend(Result.success(emptyList())) // Success with empty list
                        }
                    } else {
                        val errorBody = response.body?.string()
                        Log.e("ImageRepositoryImpl", "Get images failed: ${response.code}, Body: $errorBody")
                        trySend(Result.failure(Exception("Get images failed: ${response.code} - ${errorBody ?: "Unknown error"}")))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("ImageRepositoryImpl", "Get images error: ${e.message}", e)
            trySend(Result.failure(e))
        }
        awaitClose { Log.d("ImageRepositoryImpl", "Get Images Flow closed") }
    }

    override fun getImage(imageId: String): Flow<Result<Image>> = callbackFlow {
         try {
             if (prefsHelper.isGuestMode(context)) {
                 // Guests likely cannot fetch specific image details if it requires auth
                 trySend(Result.failure(Exception("Cannot fetch image details in guest mode.")))
                 awaitClose()
                 return@callbackFlow
            }
            Log.d("ImageRepositoryImpl", "Fetching image details for ID: $imageId")
            val request = createAuthenticatedRequest("$baseUrl/$imageId")

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        if (responseBody != null) {
                            val image = Image.fromJson(JSONObject(responseBody))
                            Log.i("ImageRepositoryImpl", "Fetched image details successfully.")
                            trySend(Result.success(image))
                        } else {
                            Log.w("ImageRepositoryImpl", "Get image successful but empty response body.")
                            trySend(Result.failure(Exception("Get image successful but empty response body.")))
                        }
                    } else {
                        val errorBody = response.body?.string()
                        Log.e("ImageRepositoryImpl", "Get image failed: ${response.code}, Body: $errorBody")
                        trySend(Result.failure(Exception("Get image failed: ${response.code} - ${errorBody ?: "Unknown error"}")))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("ImageRepositoryImpl", "Get image error: ${e.message}", e)
            trySend(Result.failure(e))
        }
        awaitClose { Log.d("ImageRepositoryImpl", "Get Image Flow closed") }
    }

    override fun deleteImage(imageId: String): Flow<Result<Unit>> = callbackFlow {
         try {
             if (prefsHelper.isGuestMode(context)) {
                 trySend(Result.failure(Exception("Cannot delete image in guest mode.")))
                 awaitClose()
                 return@callbackFlow
            }
            Log.d("ImageRepositoryImpl", "Deleting image ID: $imageId")
            val request = createAuthenticatedRequest("$baseUrl/$imageId", method = "DELETE")

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        Log.i("ImageRepositoryImpl", "Image deleted successfully.")
                        trySend(Result.success(Unit))
                    } else {
                        val errorBody = response.body?.string()
                        Log.e("ImageRepositoryImpl", "Delete image failed: ${response.code}, Body: $errorBody")
                        trySend(Result.failure(Exception("Delete image failed: ${response.code} - ${errorBody ?: "Unknown error"}")))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("ImageRepositoryImpl", "Delete image error: ${e.message}", e)
            trySend(Result.failure(e))
        }
        awaitClose { Log.d("ImageRepositoryImpl", "Delete Image Flow closed") }
    }

    // Common function for love/save toggles
    private fun toggleImageStatus(imageId: String, action: String): Flow<Result<Boolean>> = callbackFlow {
        try {
             if (prefsHelper.isGuestMode(context)) {
                 trySend(Result.failure(Exception("Cannot $action image in guest mode.")))
                 awaitClose()
                 return@callbackFlow
            }
            Log.d("ImageRepositoryImpl", "Toggling $action for image ID: $imageId")
            val request = createAuthenticatedRequest("$baseUrl/$imageId/$action", method = "PUT")

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        // Assuming backend returns { "status": true/false } or similar
                        val newStatus = responseBody?.let { JSONObject(it).optBoolean("status", false) } ?: false
                        Log.i("ImageRepositoryImpl", "Image $action toggled successfully to $newStatus.")
                        trySend(Result.success(newStatus))
                    } else {
                        val errorBody = response.body?.string()
                        Log.e("ImageRepositoryImpl", "Toggle $action failed: ${response.code}, Body: $errorBody")
                        trySend(Result.failure(Exception("Toggle $action failed: ${response.code} - ${errorBody ?: "Unknown error"}")))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("ImageRepositoryImpl", "Toggle $action error: ${e.message}", e)
            trySend(Result.failure(e))
        }
        awaitClose { Log.d("ImageRepositoryImpl", "Toggle $action Flow closed") }
    }

    override fun loveImage(imageId: String): Flow<Result<Boolean>> {
        return toggleImageStatus(imageId, "love")
    }

    fun saveImage(imageId: String): Flow<Result<Boolean>> {
         return toggleImageStatus(imageId, "save")
    }

    override fun generateImage(requestData: ImageGenerateRequest): Flow<Result<ImageGenerateResponse>> = callbackFlow {
         try {
             if (prefsHelper.isGuestMode(context)) {
                 trySend(Result.failure(Exception("Cannot generate image in guest mode.")))
                 awaitClose()
                 return@callbackFlow
            }
            Log.d("ImageRepositoryImpl", "Requesting image generation with prompt: ${requestData.prompt}")
            val jsonBody = JSONObject().apply {
                put("prompt", requestData.prompt)
                // Add other fields if needed by the backend
            }
            val request = createAuthenticatedRequest("$baseUrl/generate", method = "POST", body = jsonBody)

            withContext(Dispatchers.IO) {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val responseBody = response.body?.string()
                        if (responseBody != null) {
                            // Parse the response based on what the backend returns (e.g., task ID or the final image)
                            // Example: Assuming it returns a task ID
                            val jsonResponse = JSONObject(responseBody)
                            val taskId = jsonResponse.optString("request_id", null) // Fal task id?
                            val imageJson = jsonResponse.optJSONObject("image") // Maybe returns image directly?

                            val result = if (imageJson != null) {
                                ImageGenerateResponse(image = Image.fromJson(imageJson))
                            } else if (taskId != null) {
                                ImageGenerateResponse(taskId = taskId)
                            } else {
                                // Handle unexpected successful response format
                                Log.w("ImageRepositoryImpl", "Generate image successful but response format unknown: $responseBody")
                                ImageGenerateResponse() // Empty response
                            }
                            Log.i("ImageRepositoryImpl", "Image generation request successful.")
                            trySend(Result.success(result))
                        } else {
                             Log.w("ImageRepositoryImpl", "Generate image successful but empty response body.")
                             trySend(Result.failure(Exception("Generate image successful but empty response body.")))
                        }
                    } else {
                        val errorBody = response.body?.string()
                        Log.e("ImageRepositoryImpl", "Generate image failed: ${response.code}, Body: $errorBody")
                        trySend(Result.failure(Exception("Generate image failed: ${response.code} - ${errorBody ?: "Unknown error"}")))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("ImageRepositoryImpl", "Generate image error: ${e.message}", e)
            trySend(Result.failure(e))
        }
        awaitClose { Log.d("ImageRepositoryImpl", "Generate Image Flow closed") }
    }
}

