// Archivo: data/repository/ProfileRepository.kt
package com.univalle.unimatch.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.univalle.unimatch.presentation.viewmodel.User

class ProfileRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    fun getCurrentUserData(
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    ) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            onError("Usuario no autenticado")
            return
        }

        Log.d("ProfileRepo", "Obteniendo datos del usuario: ${currentUser.uid}")

        firestore.collection("users")
            .document(currentUser.uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    try {
                        val user = User(
                            uid = document.getString("uid") ?: "",
                            nombre = document.getString("nombre") ?: "",
                            apellido = document.getString("apellido") ?: "",
                            nombreCuenta = document.getString("nombreCuenta") ?: "",
                            email = document.getString("email") ?: "",
                            phoneNumber = document.getString("phoneNumber") ?: "",
                            birthDate = document.getString("birthDate") ?: "",
                            gender = document.getString("gender") ?: "",
                            interests = document.get("interests") as? List<String> ?: emptyList(),
                            photos = document.get("photos") as? List<String> ?: emptyList(),
                            verified = document.getBoolean("verified") ?: false
                        )

                        Log.d("ProfileRepo", "Datos obtenidos: ${user.nombre} ${user.apellido}")
                        onSuccess(user)
                    } catch (e: Exception) {
                        Log.e("ProfileRepo", "Error al parsear datos: ${e.message}")
                        onError("Error al procesar datos del usuario: ${e.message}")
                    }
                } else {
                    Log.e("ProfileRepo", "Documento de usuario no existe")
                    onError("No se encontraron datos del usuario")
                }
            }
            .addOnFailureListener { e ->
                Log.e("ProfileRepo", "Error al obtener datos: ${e.message}")
                onError("Error al obtener datos del usuario: ${e.message}")
            }
    }
}