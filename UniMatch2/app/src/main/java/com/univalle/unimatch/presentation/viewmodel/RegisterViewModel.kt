package com.univalle.unimatch.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegisterViewModel: ViewModel() {
    //private val userRepository = UserRepository() // Clase Para luego usar con Firebase

    var nombre by mutableStateOf("")
    var apellido by mutableStateOf("")
    var nombreCuenta by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var birthDate by mutableStateOf("")
    var selectedGender by mutableStateOf("")
}