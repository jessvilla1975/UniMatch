package com.univalle.unimatch.presentation.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.univalle.unimatch.data.repository.PhotoRepository

class PhotoViewModel(
    private val context: Context
) : ViewModel() {

    private val photoRepository = PhotoRepository(context)

    var selectedPhotos by mutableStateOf<List<Uri>>(emptyList())
    var savedPhotoPaths by mutableStateOf<List<String>>(emptyList())
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var showError by mutableStateOf(false)
    var successMessage by mutableStateOf<String?>(null)
    var showSuccess by mutableStateOf(false)

    // Constante para el máximo de fotos
    companion object {
        const val MAX_PHOTOS = 5
    }

    // Función para añadir fotos seleccionadas
    fun addPhotos(newPhotos: List<Uri>) {
        val totalPhotos = selectedPhotos.size + newPhotos.size
        if (totalPhotos > MAX_PHOTOS) {
            showErrorMessage("Solo puedes seleccionar máximo $MAX_PHOTOS fotos")
            return
        }
        selectedPhotos = selectedPhotos + newPhotos
    }

    // Función para remover una foto seleccionada
    fun removePhoto(uri: Uri) {
        selectedPhotos = selectedPhotos.filter { it != uri }
    }

    // Función para guardar las fotos
    fun savePhotos() {
        if (selectedPhotos.isEmpty()) {
            showErrorMessage("Debes seleccionar al menos una foto")
            return
        }

        if (selectedPhotos.size > MAX_PHOTOS) {
            showErrorMessage("Solo puedes subir máximo $MAX_PHOTOS fotos")
            return
        }

        isLoading = true
        photoRepository.savePhotos(
            photoUris = selectedPhotos,
            onSuccess = { paths ->
                isLoading = false
                savedPhotoPaths = paths
                selectedPhotos = emptyList() // Limpiar selección
                showSuccessMessage("Fotos guardadas exitosamente")
            },
            onError = { error ->
                isLoading = false
                showErrorMessage(error)
            }
        )
    }

    // Función para cargar las fotos del usuario
    fun loadUserPhotos() {
        isLoading = true
        photoRepository.getUserPhotos(
            onSuccess = { paths ->
                isLoading = false
                savedPhotoPaths = paths
            },
            onError = { error ->
                isLoading = false
                showErrorMessage(error)
            }
        )
    }

    // Función para eliminar una foto guardada
    fun deletePhoto(photoPath: String) {
        photoRepository.deletePhoto(
            photoPath = photoPath,
            onSuccess = {
                savedPhotoPaths = savedPhotoPaths.filter { it != photoPath }
                showSuccessMessage("Foto eliminada")
            },
            onError = { error ->
                showErrorMessage(error)
            }
        )
    }

    // Función para mostrar mensaje de error
    private fun showErrorMessage(message: String) {
        errorMessage = message
        showError = true
    }

    // Función para mostrar mensaje de éxito
    private fun showSuccessMessage(message: String) {
        successMessage = message
        showSuccess = true
    }

    // Función para limpiar mensajes
    fun clearMessages() {
        showError = false
        showSuccess = false
        errorMessage = null
        successMessage = null
    }

    // Función para verificar si se pueden añadir más fotos
    fun canAddMorePhotos(): Boolean {
        return selectedPhotos.size < MAX_PHOTOS
    }

    // Función para obtener el número de fotos restantes
    fun getRemainingPhotos(): Int {
        return MAX_PHOTOS - selectedPhotos.size
    }
}