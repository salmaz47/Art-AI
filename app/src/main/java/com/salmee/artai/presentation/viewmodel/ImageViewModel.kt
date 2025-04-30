package com.salmee.artai.presentation.viewmodel

import android.content.Context // Need context for SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salmee.artai.data.repository.ImageFilter
import com.salmee.artai.data.repository.ImageRepository
import com.salmee.artai.model.Image
import com.salmee.artai.model.ImageGenerateRequest
// Removed import for ImageGenerateResponse
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// Pass context to ViewModel - consider dependency injection (Hilt/Koin) in a real app
class ImageViewModel(private val imageRepository: ImageRepository, private val context: Context) : ViewModel() {

    private val _imagesResult = MutableLiveData<Result<List<Image>>>()
    val imagesResult: LiveData<Result<List<Image>>> = _imagesResult

    private val _imageDetailResult = MutableLiveData<Result<Image>>()
    val imageDetailResult: LiveData<Result<Image>> = _imageDetailResult

    private val _deleteResult = MutableLiveData<Result<Unit>>()
    val deleteResult: LiveData<Result<Unit>> = _deleteResult

    // LiveData for favorite status change (Image ID, New Status)
    private val _favoriteStatusResult = MutableLiveData<Pair<String, Result<Boolean>>>()
    val favoriteStatusResult: LiveData<Pair<String, Result<Boolean>>> = _favoriteStatusResult

    // Updated generateResult to hold Result<Image>
    private val _generateResult = MutableLiveData<Result<Image>>()
    val generateResult: LiveData<Result<Image>> = _generateResult

    // LiveData to track loading state for generation
    private val _isGenerating = MutableLiveData<Boolean>(false)
    val isGenerating: LiveData<Boolean> = _isGenerating

    // Helper to check guest mode
    private fun isGuest() = SharedPreferencesHelper.isGuestMode(context)

    fun fetchImages(filter: ImageFilter) {
        viewModelScope.launch {
            imageRepository.getImages(filter)
                .catch { e -> _imagesResult.postValue(Result.failure(e)) }
                .collect { result ->
                    _imagesResult.postValue(result)
                    // Sync local favorites after fetching for logged-in users
                    result.onSuccess { images ->
                        if (!isGuest()) {
                            SharedPreferencesHelper.syncFavoritesFromData(context, images)
                        }
                    }
                }
        }
    }

    fun fetchImageDetails(imageId: String) {
        viewModelScope.launch {
            imageRepository.getImage(imageId)
                .catch { e -> _imageDetailResult.postValue(Result.failure(e)) }
                .collect { result ->
                    _imageDetailResult.postValue(result)
                    // Optionally sync this single image's favorite status
                    result.onSuccess { image ->
                        if (!isGuest()) {
                            SharedPreferencesHelper.syncFavoritesFromData(context, listOf(image))
                        }
                    }
                }
        }
    }

    fun deleteImage(imageId: String) {
        if (isGuest()) return // Guests cannot delete
        viewModelScope.launch {
            imageRepository.deleteImage(imageId)
                .catch { e -> _deleteResult.postValue(Result.failure(e)) }
                .collect { result ->
                    _deleteResult.postValue(result)
                    // Remove from local favorites if deletion successful
                    if (result.isSuccess) {
                        SharedPreferencesHelper.removeFavorite(context, imageId)
                    }
                }
        }
    }

    // Check local favorite status
    fun isFavoriteLocally(imageId: String): Boolean {
        return SharedPreferencesHelper.isFavorite(context, imageId)
    }

    // Get all local favorite IDs
    fun getLocalFavoriteIds(): Set<String> {
        return SharedPreferencesHelper.getFavoriteImageIds(context)
    }

    // Toggle favorite: Update local immediately, sync backend for logged-in users
    fun toggleFavorite(imageId: String) {
        // 1. Toggle local status immediately and get the new status
        val newLocalStatus = SharedPreferencesHelper.toggleFavorite(context, imageId)
        _favoriteStatusResult.postValue(Pair(imageId, Result.success(newLocalStatus))) // Notify UI immediately of local change

        // 2. If logged in, sync with backend (fire and forget or handle result)
        if (!isGuest()) {
            viewModelScope.launch {
                imageRepository.loveImage(imageId) // Call the backend toggle
                    .catch { e ->
                        // Backend call failed - local state might be out of sync
                        // Optionally revert local state or notify user
                        _favoriteStatusResult.postValue(Pair(imageId, Result.failure(e))) // Notify UI of backend failure
                        // Revert local change on failure?
                        // SharedPreferencesHelper.toggleFavorite(context, imageId)
                    }
                    .collect { backendResult ->
                        backendResult.onSuccess {
                            // Backend success - ensure local matches backend state (though it should)
                            val backendStatus = it
                            if (SharedPreferencesHelper.isFavorite(context, imageId) != backendStatus) {
                                if (backendStatus) SharedPreferencesHelper.addFavorite(context, imageId)
                                else SharedPreferencesHelper.removeFavorite(context, imageId)
                                // Notify UI again if backend caused a change different from initial local toggle
                                _favoriteStatusResult.postValue(Pair(imageId, Result.success(backendStatus)))
                            }
                        }
                        backendResult.onFailure {
                            _favoriteStatusResult.postValue(Pair(imageId, Result.failure(it))) // Notify UI of backend failure
                        }
                    }
            }
        }
    }

    // Removed toggleSaveImage as it wasn't in the backend spec, assuming 'favorite' is 'love'

    fun generateImage(prompt: String) {
        if (isGuest()) {
            _generateResult.postValue(Result.failure(Exception("Please log in to generate images."))) // Inform user
            return
        }
        _isGenerating.postValue(true) // Start loading
        val request = ImageGenerateRequest(prompt = prompt)
        viewModelScope.launch {
            imageRepository.generateImage(request)
                .catch { e ->
                    _generateResult.postValue(Result.failure(e))
                    _isGenerating.postValue(false) // Stop loading on error
                }
                .collect { result -> // result is now Result<Image>
                    _generateResult.postValue(result)
                    _isGenerating.postValue(false) // Stop loading on success/failure
                }
        }
    }
}

