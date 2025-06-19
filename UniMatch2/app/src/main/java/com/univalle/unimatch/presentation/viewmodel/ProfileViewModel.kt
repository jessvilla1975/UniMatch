// Archivo: presentation/viewmodel/ProfileViewModel.kt
package com.univalle.unimatch.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univalle.unimatch.presentation.viewmodel.User
import com.univalle.unimatch.data.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    var user by mutableStateOf<User?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadUserData()
    }

    fun loadUserData() {
        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            profileRepository.getCurrentUserData(
                onSuccess = { userData ->
                    isLoading = false
                    user = userData
                },
                onError = { error ->
                    isLoading = false
                    errorMessage = error
                }
            )
        }
    }

    fun retryLoadUser() {
        loadUserData()
    }

    fun clearError() {
        errorMessage = null
    }
}