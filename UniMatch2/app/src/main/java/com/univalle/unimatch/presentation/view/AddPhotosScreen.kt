package com.univalle.unimatch.presentation.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.univalle.unimatch.presentation.viewmodel.AddPhotosViewModel
import com.univalle.unimatch.ui.theme.UvMatchTheme
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.univalle.unimatch.data.repository.AddPhotoRepository
import androidx.lifecycle.ViewModelProvider


@Composable
fun AddPhotosScreen(
    navController: NavController
) {

    val context = LocalContext.current
    // Crear el repositorio manualmente
    val repository = remember { AddPhotoRepository() }

    // Crear el ViewModel manualmente con una factory personalizada
    val viewModel: AddPhotosViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AddPhotosViewModel(repository, context) as T
            }
        }
    )

    val photoUris = viewModel.photoUris
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.addPhoto(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Agrega fotos", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold))

        Spacer(modifier = Modifier.height(8.dp))

        Text("Agrega al menos 2 fotos para poder continuar", color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        // Grid de fotos
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        ) {
            items(photoUris.size) { index ->
                val uri = photoUris[index]
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray)
                        .clickable {
                            if (uri == null) {
                                imagePickerLauncher.launch("image/*")
                            } else {
                                viewModel.removePhoto(index)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (uri == null) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar foto",
                            tint = Color.Gray,
                            modifier = Modifier.size(40.dp)
                        )
                    } else {
                        AsyncImage(
                            model = uri,
                            contentDescription = "Foto $index",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (viewModel.isLoading){
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    if (viewModel.canContinue()) {
                        viewModel.uploadPhotos (
                            onSuccess = {
                                navController.navigate("Welcome_screen"){
                                    popUpTo("Interests_screen") { inclusive = true }
                                }
                            },
                            onError = { message ->
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        )
                    } else {
                        Toast.makeText(context, "Agrega al menos 2 fotos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD81B60)
                )
            ) {
                Text("CONTINUAR", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddPhotosScreenPreview() {
    UvMatchTheme {
        AddPhotosScreen( navController = NavController(LocalContext.current))
    }
}



