package com.univalle.unimatch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class InterestsViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun guardarIntereses(intereses: List<String>, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            onError("Usuario no autenticado")
            return
        }

        val datos = mapOf("interests" to intereses)
        firestore.collection("users").document(uid)
            .update(datos)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e.message ?: "Error al guardar intereses") }
    }
}