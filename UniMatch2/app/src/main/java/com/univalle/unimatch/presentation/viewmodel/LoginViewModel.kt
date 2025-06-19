package com.univalle.unimatch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.univalle.unimatch.data.repository.GoogleAuthClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class LoginViewModel(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {

    val loginResult = MutableStateFlow<Result<Boolean>?>(null)

    open fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loginResult.value = Result.success(true)
                } else {
                    loginResult.value = Result.failure(task.exception ?: Exception("Credenciales incorrectas"))
                }
            }
    }

    fun resetLoginResult() {
        loginResult.value = null
    }
}

