package com.univalle.unimatch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.univalle.unimatch.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PasswordRecoveryViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {
    val email = MutableStateFlow("")
    val code = MutableStateFlow("")
    val newPassword = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        object CodeSent : UiState()
        object CodeValidated : UiState()
        object PasswordChanged : UiState()
        data class Error(val message: String) : UiState()
    }

    fun sendCode() {
        _uiState.value = UiState.Loading
        authRepository.sendPasswordResetCode(
            email.value,
            onSuccess = { _uiState.value = UiState.CodeSent },
            onError = { _uiState.value = UiState.Error(it) }
        )
    }

    fun validateCode() {
        _uiState.value = UiState.Loading
        authRepository.validateResetCode(
            email.value,
            code.value,
            onSuccess = { _uiState.value = UiState.CodeValidated },
            onError = { _uiState.value = UiState.Error(it) }
        )
    }

    fun changePassword() {
        if (newPassword.value != confirmPassword.value) {
            _uiState.value = UiState.Error("Las contraseñas no coinciden")
            return
        }
        _uiState.value = UiState.Loading
        authRepository.updatePassword(
            email.value,
            newPassword.value,
            onSuccess = { _uiState.value = UiState.PasswordChanged },
            onError = { _uiState.value = UiState.Error(it) }
        )
    }

    fun resetState() {
        _uiState.value = UiState.Idle
    }
} 