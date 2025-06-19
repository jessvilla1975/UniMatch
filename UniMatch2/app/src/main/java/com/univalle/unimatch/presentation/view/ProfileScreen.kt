// Archivo: presentation/view/ProfileScreen.kt
package com.univalle.unimatch.presentation.view

import androidx.compose.runtime.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.univalle.unimatch.R
import com.univalle.unimatch.ui.theme.UvMatchTheme
import com.univalle.unimatch.presentation.viewmodel.ProfileViewModel
import com.univalle.unimatch.data.repository.ProfileRepository
import coil.compose.AsyncImage

@Composable
fun ProfileScreen(navController: NavController) {

    // Crear el repositorio y ViewModel
    val repository = remember { ProfileRepository() }
    val viewModel: ProfileViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProfileViewModel(repository) as T
            }
        }
    )

    val user = viewModel.user
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color(0xFFFFFFFF),
            )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFFB30811), Color(0xFF000000)),
                        start = Offset.Zero,
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    )
                )
                .verticalScroll(rememberScrollState())
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 53.dp,bottom = 43.dp,)
                    .fillMaxWidth()
            ){
                // Top icons (barra superior)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 70.dp, end = 70.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón: Perfil
                    Image(
                        painter = painterResource(id = R.drawable.ic_user_white),
                        contentDescription = "Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color(0xFFDF0814), shape = CircleShape)
                    )

                    // Botón: Logo central
                    IconButton(
                        onClick = { navController.navigate("Home_screen") },
                        modifier = Modifier
                            .size(65.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_uvmatch),
                            contentDescription = "Home",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(55.dp)
                                .background(Color(0xFFDF0814), shape = CircleShape)
                        )
                    }

                    // Botón: Chat
                    IconButton(
                        onClick = { navController.navigate("Chat_screen") },
                        modifier = Modifier
                            .size(65.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_chat_white),
                            contentDescription = "Chat",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(55.dp)
                                .background(Color(0xFFDF0814), shape = CircleShape)
                        )
                    }
                }

                // Imagen de perfil
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top=15.dp, bottom = 10.dp)
                        .fillMaxWidth()
                ){
                    if (isLoading) {
                        // Mostrar indicador de carga mientras se obtienen los datos
                        Box(
                            modifier = Modifier
                                .size(250.dp)
                                .clip(CircleShape)
                                .background(Color.Gray.copy(alpha = 0.3f)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.White)
                        }
                    } else if (user?.getProfilePhoto() != null) {
                        // Mostrar la primera foto del usuario desde Firebase
                        AsyncImage(
                            model = user.getProfilePhoto(),
                            contentDescription = "Foto de perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(250.dp)
                                .clip(CircleShape),
                            placeholder = painterResource(id = R.drawable.perfil),
                            error = painterResource(id = R.drawable.perfil)
                        )
                    } else {
                        // Imagen por defecto si no hay foto
                        Image(
                            painter = painterResource(id = R.drawable.perfil),
                            contentDescription = "Imagen de perfil por defecto",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(250.dp)
                                .clip(CircleShape)
                        )
                    }
                }

                // Nombre y edad
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top=10.dp, bottom = 5.dp)
                        .fillMaxWidth()
                ){
                    if (isLoading) {
                        // Placeholder mientras carga
                        Box(
                            modifier = Modifier
                                .width(200.dp)
                                .height(40.dp)
                                .background(
                                    Color.White.copy(alpha = 0.3f),
                                    RoundedCornerShape(8.dp)
                                )
                        )
                    } else if (user != null) {
                        Text(
                            text = "${user.getFullName()}, ${user.getAge()}",
                            color = Color(0xFFFFFFFF),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    } else {
                        Text(
                            text = "Usuario, --",
                            color = Color(0xFFFFFFFF),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }

                // Descripción (puedes usar nombreCuenta o interests)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(bottom = 53.dp)
                        .fillMaxWidth()
                ){
                    if (isLoading) {
                        // Placeholder mientras carga
                        Box(
                            modifier = Modifier
                                .width(295.dp)
                                .height(60.dp)
                                .background(
                                    Color.White.copy(alpha = 0.3f),
                                    RoundedCornerShape(8.dp)
                                )
                        )
                    } else if (user != null) {
                        val interestsText = if (user.interests.isNotEmpty()) {
                            "Intereses: ${user.interests.take(3).joinToString(", ")}"
                        } else {
                            "@${user.nombreCuenta}"
                        }

                        Text(
                            text = interestsText,
                            color = Color(0xFFFFFFFF),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(295.dp)
                        )
                    } else {
                        Text(
                            text = "Cargando información...",
                            color = Color(0xFFFFFFFF),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(295.dp)
                        )
                    }
                }

                // Mostrar error si existe
                errorMessage?.let { error ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Error: $error",
                            color = Color.Red,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )

                        TextButton(
                            onClick = { viewModel.retryLoadUser() }
                        ) {
                            Text(
                                text = "Reintentar",
                                color = Color.White
                            )
                        }
                    }
                }

                // Botones de acción
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(bottom = 40.dp)
                        .fillMaxWidth()
                ){
                    Row(
                    ){
                        IconButton(
                            onClick = { /* Settings */ },
                            modifier = Modifier
                                .padding(end=40.dp)
                                .size(80.dp)
                                .border(width = 1.dp, color = Color.Red, shape = CircleShape)
                                .background(color = Color.White, shape = CircleShape)
                                .padding(12.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_settings),
                                contentDescription = "Settings",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(65.dp)
                            )
                        }
                        IconButton(
                            onClick = { /* Camera */ },
                            modifier = Modifier
                                .padding(top=40.dp, end=40.dp)
                                .size(80.dp)
                                .border(width = 1.dp, color = Color.Red, shape = CircleShape)
                                .background(color = Color.White, shape = CircleShape)
                                .padding(12.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_camera),
                                contentDescription = "Camera",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(70.dp)
                            )
                        }
                        IconButton(
                            onClick = { /* Edit */ },
                            modifier = Modifier
                                .size(80.dp)
                                .border(width = 1.dp, color = Color.Red, shape = CircleShape)
                                .background(color = Color.White, shape = CircleShape)
                                .padding(12.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_edit),
                                contentDescription = "Edit",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(70.dp)
                            )
                        }
                    }
                }

                // Botón de logout
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .padding(bottom = 23.dp)
                        .fillMaxWidth()
                ){
                    Image(
                        painter = painterResource(id = R.drawable.ic_logout_white),
                        contentDescription = "Logout",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .width(57.dp)
                            .height(57.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    UvMatchTheme {
        ProfileScreen(navController = rememberNavController())
    }
}