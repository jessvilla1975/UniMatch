package com.univalle.unimatch.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class AddPhotoRepository (
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val storage: FirebaseStorage = FirebaseStorage.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    fun uploadPhotosToStorage(
        uris: List<Uri>,
        context: Context,
        onSuccess: (List<String>) -> Unit,
        onError: (String) -> Unit
    ) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Log.e("PhotoRepo", "Usuario no autenticado")
            onError("Usuario no autenticado")
            return
        }

        Log.d("PhotoRepo", "UID: $userId")
        val photoUrls = mutableListOf<String>()
        var uploadsCompleted = 0

        uris.forEachIndexed { index, uri ->
            Log.d("PhotoRepo", "Subiendo archivo: $uri")

            val file = getFileFromUri(context, uri)
            if (file == null) {
                Log.e("PhotoRepo", "No se pudo crear archivo temporal desde URI: $uri")
                onError("No se pudo procesar la imagen")
                return@forEachIndexed
            }

            val fileUri = Uri.fromFile(file)
            val ref = storage.reference.child("users/$userId/photos/photo_$index.jpg")

            ref.putFile(fileUri)
                .addOnSuccessListener {
                    ref.downloadUrl
                        .addOnSuccessListener { downloadUri ->
                            Log.d("PhotoRepo", "URL obtenida: $downloadUri")
                            photoUrls.add(downloadUri.toString())
                            uploadsCompleted++

                            if (uploadsCompleted == uris.size) {
                                saveUrlsToFirestore(userId, photoUrls, onSuccess, onError)
                            }
                        }
                        .addOnFailureListener { e ->
                            Log.e("PhotoRepo", "Error al obtener URL: ${e.message}")
                            onError("Error al obtener URL de la foto: ${e.message}")
                        }
                }
                .addOnFailureListener { error ->
                    Log.e("PhotoRepo", "Error al subir foto: ${error.message}")
                    onError("Error al subir foto: ${error.message}")
                }
        }
    }

    private fun saveUrlsToFirestore(
        userId: String,
        photoUrls: List<String>,
        onSuccess: (List<String>) -> Unit,
        onError: (String) -> Unit
    ) {
        val data = mapOf("photos" to photoUrls)

        firestore.collection("users")
            .document(userId)
            .update(data)
            .addOnSuccessListener {
                Log.d("PhotoRepo", "URLs guardadas correctamente")
                onSuccess(photoUrls)
            }
            .addOnFailureListener {
                Log.e("PhotoRepo", "Error al guardar en Firestore: ${it.message}")
                onError("Error al guardar en Firestore: ${it.message}")
            }
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
            tempFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}