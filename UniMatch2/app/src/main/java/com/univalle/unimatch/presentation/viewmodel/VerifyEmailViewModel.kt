package com.univalle.unimatch.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.univalle.unimatch.data.repository.AuthRepository

class VerifyEmailViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    var isVerified by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun checkEmailVerified(onVerified: () -> Unit, onNotVerified: () -> Unit) {
        val user = authRepository.getCurrentUser()

        if (user != null) {
            isLoading = true
            user.reload().addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {
                    if (user.isEmailVerified) {
                        isVerified = true
                        authRepository.actualizarVerificadoEnFirestore(user.uid) {
                            onVerified()
                        }
                    } else {
                        isVerified = false
                        onNotVerified()
                    }
                } else {
                    errorMessage =
                        "Error al verificar el estado del correo: ${task.exception?.message}"
                }
            }
        } else {
            errorMessage = "Usuario no autenticado"
        }
    }
}