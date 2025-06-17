package com.univalle.unimatch.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

class PhotoRepository(
    private val context: Context,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // Función para guardar una foto localmente
    private fun savePhotoLocally(uri: Uri): String? {
        return try {
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            val filename = "photo_${UUID.randomUUID()}.jpg"
            val file = File(context.filesDir, filename)

            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            }

            file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    // Función para guardar múltiples fotos
    fun savePhotos(
        photoUris: List<Uri>,
        onSuccess: (List<String>) -> Unit,
        onError: (String) -> Unit
    ) {
        val savedPaths = mutableListOf<String>()

        try {
            photoUris.forEach { uri ->
                val path = savePhotoLocally(uri)
                if (path != null) {
                    savedPaths.add(path)
                } else {
                    onError("Error al guardar una de las fotos")
                    return
                }
            }

            // Guardar las rutas en Firebase
            savePhotoPathsToFirebase(savedPaths, onSuccess, onError)

        } catch (e: Exception) {
            onError("Error al procesar las fotos: ${e.message}")
        }
    }

    // Función para guardar las rutas en Firebase
    private fun savePhotoPathsToFirebase(
        photoPaths: List<String>,
        onSuccess: (List<String>) -> Unit,
        onError: (String) -> Unit
    ) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            onError("Usuario no autenticado")
            return
        }

        val userEmail = currentUser.email ?: ""
        val photoData = mapOf(
            "userEmail" to userEmail,
            "photoPaths" to photoPaths,
            "timestamp" to System.currentTimeMillis()
        )

        firestore.collection("user_photos")
            .document(currentUser.uid)
            .set(photoData)
            .addOnSuccessListener {
                onSuccess(photoPaths)
            }
            .addOnFailureListener { e ->
                onError("Error al guardar en Firebase: ${e.message}")
            }
    }

    // Función para obtener las fotos del usuario
    fun getUserPhotos(
        onSuccess: (List<String>) -> Unit,
        onError: (String) -> Unit
    ) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            onError("Usuario no autenticado")
            return
        }

        firestore.collection("user_photos")
            .document(currentUser.uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val photoPaths = document.get("photoPaths") as? List<String> ?: emptyList()
                    onSuccess(photoPaths)
                } else {
                    onSuccess(emptyList())
                }
            }
            .addOnFailureListener { e ->
                onError("Error al obtener fotos: ${e.message}")
            }
    }

    // Función para eliminar una foto
    fun deletePhoto(
        photoPath: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        // Eliminar archivo local
        val file = File(photoPath)
        if (file.exists()) {
            file.delete()
        }

        // Actualizar Firebase
        getUserPhotos(
            onSuccess = { currentPaths ->
                val updatedPaths = currentPaths.filter { it != photoPath }
                updatePhotoPathsInFirebase(updatedPaths, onSuccess, onError)
            },
            onError = onError
        )
    }

    // Función para actualizar las rutas en Firebase
    private fun updatePhotoPathsInFirebase(
        photoPaths: List<String>,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            onError("Usuario no autenticado")
            return
        }

        firestore.collection("user_photos")
            .document(currentUser.uid)
            .update("photoPaths", photoPaths)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError("Error al actualizar: ${e.message}") }
    }
}