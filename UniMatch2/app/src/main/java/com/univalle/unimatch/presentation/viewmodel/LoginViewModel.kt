package com.univalle.unimatch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.univalle.unimatch.data.repository.GoogleAuthClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class LoginViewModel: ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _loginResult = MutableStateFlow<Result<Boolean>?>(null)
    open val loginResult: StateFlow<Result<Unit>?> = _loginResult

    lateinit var googleAuthClient: GoogleAuthClient

    open fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginResult.value = Result.success(true)
                } else {
                    _loginResult.value = Result.failure(task.exception ?: Exception("Credenciales incorrectas"))
                }
            }
    }

    open fun resetLoginResult() {
        _loginResult.value = null
    }

    fun setGoogleClient(client: GoogleAuthClient) {
        googleAuthClient = client
    }

}