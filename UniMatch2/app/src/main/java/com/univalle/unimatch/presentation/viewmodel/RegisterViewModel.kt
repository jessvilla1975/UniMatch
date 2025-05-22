package com.univalle.unimatch.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.univalle.unimatch.data.repository.AuthRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class RegisterViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {
    var nombre by mutableStateOf("")
    var apellido by mutableStateOf("")
    var nombreCuenta by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var birthDate by mutableStateOf("")
    var selectedGender by mutableStateOf("")

    var nombreError by mutableStateOf<String?>(null)
    var apellidoError by mutableStateOf<String?>(null)
    var nombreCuentaError by mutableStateOf<String?>(null)
    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)
    var confirmPasswordError by mutableStateOf<String?>(null)
    var phoneNumberError by mutableStateOf<String?>(null)
    var birthDateError by mutableStateOf<String?>(null)
    var selectedGenderError by mutableStateOf<String?>(null)

    var errorMessage by mutableStateOf<String?>(null)
    var showError by mutableStateOf(false)

    var isLoading by mutableStateOf(false)

    fun onNombreChange(value: String) {
        nombre = value
        nombreError = if (!value.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$"))) "Solo letras" else null
    }

    fun onApellidoChange(value: String) {
        apellido = value
        apellidoError = if (!value.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$"))) "Solo letras" else null
    }

    fun onNombreCuentaChange(value: String) {
        nombreCuenta = value
        nombreCuentaError = if (value.isBlank()) "Campo obligatorio" else null
    }

    fun onEmailChange(value: String) {
        email = value
        emailError = if (!value.endsWith("@correounivalle.edu.co")) "Correo no válido solo se permiten correos @correounivalle.edu.co" else null
    }

    fun onPasswordChange(value: String) {
        password = value
        passwordError = if (value.length < 6) "Mínimo 6 caracteres" else null
        confirmPasswordError = if (confirmPassword != password) "No coinciden" else null
    }

    fun onConfirmPasswordChange(value: String) {
        confirmPassword = value
        confirmPasswordError = if (value != password) "No coinciden" else null
    }

    fun onPhoneNumberChange(value: String) {
        phoneNumber = value
        phoneNumberError = if (!value.all { it.isDigit() } || value.length != 10) "10 dígitos" else null
    }

    fun onBirthDateChange(value: String) {
        birthDate = value
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.isLenient = false
            val date = sdf.parse(value)
            val today = Calendar.getInstance()

            if (date != null && date.after(today.time)) {
                birthDateError = "Fecha futura inválida"
                return
            }
            today.add(Calendar.YEAR, -18)
            if (date != null && date.after(today.time)) {
                birthDateError = "Debes tener al menos 18 años"
                return
            }
            birthDateError = null
        } catch (e: Exception) {
            birthDateError = "Formato inválido"
        }
    }

    fun onSelectedGenderChange(value: String) {
        selectedGender = value
        selectedGenderError = if (selectedGender.isBlank()) "Debes seleccionar un género" else null

    }

    fun iniciarRegistro(
        context: Context,
        onSuccess: () -> Unit,
    ) {
        if (!validarCampos()) {
            errorMessage = ("Corrige los errores antes de continuar")
            showError = true
            return
        }
        isLoading = true
        authRepository.registerUser(
            email = email,
            password = password,
            nombre = nombre,
            apellido = apellido,
            nombreCuenta = nombreCuenta,
            phoneNumber = phoneNumber,
            birthDate = birthDate,
            gender = selectedGender,
            onSuccess = {
                isLoading = false
                onSuccess()
            },
            onError = { mensaje ->
                isLoading = false
                errorMessage = mensaje
                showError = true
            }
        )
    }

    fun validarCampos(): Boolean {
        onNombreChange(nombre)
        onApellidoChange(apellido)
        onNombreCuentaChange(nombreCuenta)
        onEmailChange(email)
        onPasswordChange(password)
        onConfirmPasswordChange(confirmPassword)
        onPhoneNumberChange(phoneNumber)
        onBirthDateChange(birthDate)
        onSelectedGenderChange(selectedGender)

        return listOf(
            nombreError,
            apellidoError,
            nombreCuentaError,
            emailError,
            passwordError,
            confirmPasswordError,
            phoneNumberError,
            birthDateError,
            selectedGenderError
        ).all { it == null }
    }
}