package com.salmee.artai.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salmee.artai.data.repository.AuthRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// Updated AuthViewModel to use LiveData for results
class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _signupResult = MutableLiveData<Result<Boolean>>()
    val signupResult: LiveData<Result<Boolean>> = _signupResult

    private val _loginResult = MutableLiveData<Result<Boolean>>()
    val loginResult: LiveData<Result<Boolean>> = _loginResult

    private val _resetPasswordResult = MutableLiveData<Result<Unit>>()
    val resetPasswordResult: LiveData<Result<Unit>> = _resetPasswordResult

    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            authRepository.signup(name, email, password)
                .catch { e -> _signupResult.postValue(Result.failure(e)) }
                .collect { _signupResult.postValue(it) }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(email, password)
                .catch { e -> _loginResult.postValue(Result.failure(e)) }
                .collect { _loginResult.postValue(it) }
        }
    }

    fun sendPasswordReset(email: String) {
        viewModelScope.launch {
            authRepository.resetPassword(email)
                .catch { e -> _resetPasswordResult.postValue(Result.failure(e)) }
                .collect { _resetPasswordResult.postValue(it) }
        }
    }

    // Logout is still synchronous in the repository
    fun logout() {
        authRepository.logout()
        // No LiveData needed for logout in this ViewModel, handled in UserViewModel or directly
    }
}

