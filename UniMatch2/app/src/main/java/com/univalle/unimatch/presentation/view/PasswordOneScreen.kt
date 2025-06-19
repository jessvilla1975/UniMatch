package com.univalle.unimatch.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.univalle.unimatch.R
import com.univalle.unimatch.ui.theme.UvMatchTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.univalle.unimatch.presentation.viewmodel.PasswordRecoveryViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PasswordOneScreen(
    navController: NavController,
    onBackClick: () -> Unit = {},
    viewModel: PasswordRecoveryViewModel = viewModel()
) {
    val email = viewModel.email.collectAsState().value
    val uiState = viewModel.uiState.collectAsState().value
    var showError by remember { mutableStateOf<String?>(null) }
    var showSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        when (uiState) {
            is PasswordRecoveryViewModel.UiState.CodeSent -> {
                showSuccess = true
                viewModel.resetState()
            }
            is PasswordRecoveryViewModel.UiState.Error -> {
                showError = uiState.message
            }
            else -> {}
        }
    }

    // Mostrar mensaje de éxito durante 5 segundos y luego navegar
    LaunchedEffect(showSuccess) {
        if (showSuccess) {
            kotlinx.coroutines.delay(5000)
            navController.navigate("Login_screen")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFFB30811), Color(0xFF000000)),
                    start = Offset.Zero,
                    end = Offset(0f, Float.POSITIVE_INFINITY))
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp)) // Bajamos todo el contenido

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Spacer(modifier = Modifier.weight(0.5f)) // Empuja desde la izquierda

// A:       //Logo de la app
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(200.dp), // Aumentado de 40.dp a 60.d
            )
            Spacer(modifier = Modifier.weight(1.2f)) // Empuja desde la derecha
        }

        Spacer(modifier = Modifier.height(6.dp)) // También bajamos el contenido inferior

        Text(
            text = "RECUPERACIÓN DE CONTRASEÑA",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Ingresa tu correo electrónico",
            fontSize = 12.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.email.value = it },
            placeholder = { Text("ejemplo@correounivalle.edu.com") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email"
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.sendCode() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFFB00020))
        ) {
            Text(
                text = "ENVIAR CÓDIGO",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (showError != null) {
            Text(showError!!, color = Color.Yellow, modifier = Modifier.padding(top = 8.dp))
        }

        if (showSuccess) {
            Text("Correo enviado con éxito", color = Color.Green, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Preview(
    showBackground = true,
    name = "Password Recovery Screen",
    device = "id:pixel_4",
    showSystemUi = true
)
@Composable
fun PasswordOneScreenPreview() {
    UvMatchTheme {
        PasswordOneScreen(navController = rememberNavController())
    }
}