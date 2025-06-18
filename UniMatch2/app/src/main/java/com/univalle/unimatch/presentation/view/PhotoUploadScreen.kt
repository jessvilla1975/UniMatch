package com.univalle.unimatch.presentation.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.univalle.unimatch.presentation.viewmodel.PhotoViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoUploadScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel = remember { PhotoViewModel(context) }

    // Launcher para seleccionar múltiples fotos
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        if (uris.isNotEmpty()) {
            viewModel.addPhotos(uris)
        }
    }

    // Cargar fotos del usuario al iniciar
    LaunchedEffect(Unit) {
        viewModel.loadUserPhotos()
    }

    // Mostrar alertas de error y éxito
    if (viewModel.showError) {
        AlertDialog(
            onDismissRequest = { viewModel.clearMessages() },
            title = { Text("Error") },
            text = { Text(viewModel.errorMessage ?: "") },
            confirmButton = {
                TextButton(onClick = { viewModel.clearMessages() }) {
                    Text("Aceptar")
                }
            }
        )
    }

    if (viewModel.showSuccess) {
        AlertDialog(
            onDismissRequest = { viewModel.clearMessages() },
            title = { Text("Éxito") },
            text = { Text(viewModel.successMessage ?: "") },
            confirmButton = {
                TextButton(onClick = { viewModel.clearMessages() }) {
                    Text("Aceptar")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCD1F32))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Mis Fotos",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Contador de fotos
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Fotos seleccionadas: ${viewModel.selectedPhotos.size}/${PhotoViewModel.MAX_PHOTOS}",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFCD1F32)
                        )
                        Text(
                            text = "Fotos guardadas: ${viewModel.savedPhotoPaths.size}",
                            color = Color.Gray
                        )
                    }

                    if (viewModel.canAddMorePhotos()) {
                        Text(
                            text = "Puedes añadir ${viewModel.getRemainingPhotos()} más",
                            color = Color.Green,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        if (viewModel.canAddMorePhotos()) {
                            multiplePhotoPickerLauncher.launch("image/*")
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = viewModel.canAddMorePhotos() && !viewModel.isLoading,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFFCD1F32))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Seleccionar", color = Color(0xFFCD1F32))
                }

                Button(
                    onClick = { viewModel.savePhotos() },
                    modifier = Modifier.weight(1f),
                    enabled = viewModel.selectedPhotos.isNotEmpty() && !viewModel.isLoading,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                ) {
                    Text("Guardar", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fotos seleccionadas (pendientes de guardar)
            if (viewModel.selectedPhotos.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Fotos seleccionadas (pendientes)",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFCD1F32)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(viewModel.selectedPhotos) { uri ->
                                Box {
                                    AsyncImage(
                                        model = uri,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )

                                    IconButton(
                                        onClick = { viewModel.removePhoto(uri) },
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .size(24.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Close,
                                            contentDescription = "Eliminar",
                                            tint = Color.Red,
                                            modifier = Modifier
                                                .background(
                                                    Color.White,
                                                    RoundedCornerShape(12.dp)
                                                )
                                                .padding(2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Fotos guardadas
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Fotos guardadas",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFCD1F32)
                        )

                        if (viewModel.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color(0xFFCD1F32)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (viewModel.savedPhotoPaths.isEmpty()) {
                        Text(
                            text = "No tienes fotos guardadas",
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    } else {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(viewModel.savedPhotoPaths) { path ->
                                Box {
                                    AsyncImage(
                                        model = File(path),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )

                                    IconButton(
                                        onClick = { viewModel.deletePhoto(path) },
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .size(24.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Eliminar",
                                            tint = Color.Red,
                                            modifier = Modifier
                                                .background(
                                                    Color.White,
                                                    RoundedCornerShape(12.dp)
                                                )
                                                .padding(2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}