package com.univalle.unimatch.presentation.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

open class AddPhotosViewModel : ViewModel() {

    private val maxPhotos = 6
    val photoUris = mutableStateListOf<Uri?>().apply {
        repeat(maxPhotos) { add(null) }
    }

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
}