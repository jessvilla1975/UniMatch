package com.univalle.unimatch.presentation.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univalle.unimatch.data.repository.AddPhotoRepository
import kotlinx.coroutines.launch

class AddPhotosViewModel (
    private val addPhotoRepository: AddPhotoRepository,
    private val context: Context
) : ViewModel() {

    private val maxPhotos = 6
    val photoUris = mutableStateListOf<Uri?>().apply {
        repeat(maxPhotos) { add(null) }
    }

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun addPhoto(uri: Uri) {
        val index = photoUris.indexOfFirst { it == null }
        if (index != -1) {
            photoUris[index] = uri
        }
    }

    fun removePhoto(index: Int) {
        if (index in photoUris.indices) {
            photoUris[index] = null
        }
    }

    fun canContinue(): Boolean {
        return photoUris.filterNotNull().size >= 2
    }

    fun uploadPhotos(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val selectedUris = photoUris.filterNotNull()

        isLoading = true

        addPhotoRepository.uploadPhotosToStorage(
            uris = selectedUris,
            context = context,
            onSuccess = {
                isLoading = false
                onSuccess()
            },
            onError = {
                isLoading = false
                errorMessage = it
                onError(it)
            }
        )
    }
}