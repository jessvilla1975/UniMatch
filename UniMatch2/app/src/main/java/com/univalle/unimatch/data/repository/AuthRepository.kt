package com.univalle.unimatch.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    fun registerUser(
        email: String,
        password: String,
        nombre: String,
        apellido: String,
        nombreCuenta: String,
        phoneNumber: String,
        birthDate: String,
        gender: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val uid = user?.uid

                    val userData = mapOf(
                        "uid" to uid,
                        "nombre" to nombre,
                        "apellido" to apellido,
                        "nombreCuenta" to nombreCuenta,
                        "email" to email,
                        "phoneNumber" to phoneNumber,
                        "birthDate" to birthDate,
                        "gender" to gender,
                        "verified" to false
                    )

                    uid?.let {
                        firestore.collection("users")
                            .document(it)
                            .set(userData)
                            // se utiliza para enviar el correo de verificacion
                            .addOnSuccessListener {
                                user.sendEmailVerification().addOnCompleteListener { emailTask ->
                                    if (emailTask.isSuccessful) {
                                        onSuccess()
                                    } else {
                                        onError("Error al enviar correo de verificación: ${emailTask.exception?.message}")
                                    }
                                }
                            }
                            .addOnFailureListener { e ->
                                onError("Error al guardar datos en Firestore: ${e.message}")
                            }
                    }
                } else {
                    onError("Error al registrar usuario: ${task.exception?.message}")
                }
            }
    }
    fun actualizarVerificadoEnFirestore(uid: String, onComplete: () -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(uid)
            .update("verified", true)
            .addOnSuccessListener { onComplete() }
            .addOnFailureListener { onComplete() } // Igual continúa para no bloquear la app
    }
    fun getCurrentUser() = auth.currentUser

    fun sendPasswordResetCode(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        // Generar código de 6 dígitos
        val code = (100000..999999).random().toString()
        // Guardar el código en Firestore asociado al email
        firestore.collection("password_reset_codes")
            .document(email)
            .set(mapOf("code" to code, "timestamp" to System.currentTimeMillis()))
            .addOnSuccessListener {
                // Enviar el código al correo usando Firebase Auth (no soporta custom email, así que solo para ejemplo)
                // En producción, usar un backend o servicio de email
                auth.sendPasswordResetEmail(email)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { e -> onError("Error al enviar correo: ${e.message}") }
            }
            .addOnFailureListener { e -> onError("Error al guardar código: ${e.message}") }
    }

    fun validateResetCode(
        email: String,
        code: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        firestore.collection("password_reset_codes")
            .document(email)
            .get()
            .addOnSuccessListener { document ->
                val savedCode = document.getString("code")
                if (savedCode == code) {
                    onSuccess()
                } else {
                    onError("Código incorrecto")
                }
            }
            .addOnFailureListener { e -> onError("Error al validar código: ${e.message}") }
    }

    fun updatePassword(
        email: String,
        newPassword: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        // Para cambiar la contraseña, el usuario debe estar autenticado. En un flujo real, se debe usar un backend seguro.
        // Aquí solo se muestra el método de Firebase para usuarios autenticados.
        val user = auth.currentUser
        if (user != null && user.email == email) {
            user.updatePassword(newPassword)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { e -> onError("Error al actualizar contraseña: ${e.message}") }
        } else {
            onError("Usuario no autenticado. No se puede cambiar la contraseña directamente desde el cliente.")
        }
    }
}