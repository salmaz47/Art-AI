package com.salmee.artai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salmee.artai.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        authRepository.login(email, password)
            .onEach { result ->
                result.fold(
                    onSuccess = { onResult(true, null) },
                    onFailure = { onResult(false, it.message) }
                )
            }
            .launchIn(viewModelScope)
    }

    fun signup(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        authRepository.signup(email, password)
            .onEach { result ->
                result.fold(
                    onSuccess = { onResult(true, null) },
                    onFailure = { onResult(false, it.message) }
                )
            }
            .launchIn(viewModelScope)
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
