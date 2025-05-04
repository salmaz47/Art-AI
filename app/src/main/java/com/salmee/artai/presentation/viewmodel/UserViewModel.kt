package com.salmee.artai.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salmee.artai.data.repository.AuthRepository
import com.salmee.artai.data.repository.UserRepository
import com.salmee.artai.model.User
import com.salmee.artai.model.UserProfileUpdate
import com.salmee.artai.model.UserPasswordUpdate
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository, private val authRepository: AuthRepository) : ViewModel() {

    private val _userProfile = MutableLiveData<Result<User>>()
    val userProfile: LiveData<Result<User>> = _userProfile

    private val _updateProfileResult = MutableLiveData<Result<User>>()
    val updateProfileResult: LiveData<Result<User>> = _updateProfileResult

    private val _updatePasswordResult = MutableLiveData<Result<Unit>>()
    val updatePasswordResult: LiveData<Result<Unit>> = _updatePasswordResult

    private val _deleteAccountResult = MutableLiveData<Result<Unit>>()
    val deleteAccountResult: LiveData<Result<Unit>> = _deleteAccountResult

    private val _logoutResult = MutableLiveData<Boolean>()
    val logoutResult: LiveData<Boolean> = _logoutResult

    fun fetchUserProfile() {
        viewModelScope.launch {
            userRepository.getProfile()
                .catch { e -> _userProfile.postValue(Result.failure(e)) }
                .collect { _userProfile.postValue(it) }
        }
    }

    fun updateUserProfile(profileUpdate: UserProfileUpdate) {
        viewModelScope.launch {
            userRepository.updateProfile(profileUpdate)
                .catch { e -> _updateProfileResult.postValue(Result.failure(e)) }
                .collect { _updateProfileResult.postValue(it) }
        }
    }

    fun updateUserPassword(passwordUpdate: UserPasswordUpdate) {
        viewModelScope.launch {
            userRepository.updatePassword(passwordUpdate)
                .catch { e -> _updatePasswordResult.postValue(Result.failure(e)) }
                .collect { _updatePasswordResult.postValue(it) }
        }
    }

    fun deleteUserAccount() {
        viewModelScope.launch {
            userRepository.deleteAccount()
                .catch { e -> _deleteAccountResult.postValue(Result.failure(e)) }
                .collect { result -> 
                    _deleteAccountResult.postValue(result)
                    // Optionally trigger logout logic here if deletion is successful
                    if (result.isSuccess) {
                        authRepository.logout() // Ensure token is cleared
                    }
                 }
        }
    }

    fun logout() {
        // Logout is synchronous in the current AuthRepository implementation
        authRepository.logout()
        _logoutResult.postValue(true) // Indicate logout completed
    }
}

