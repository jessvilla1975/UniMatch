package com.univalle.unimatch.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.univalle.unimatch.R
import com.univalle.unimatch.ui.theme.UvMatchTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.univalle.unimatch.presentation.viewmodel.PasswordRecoveryViewModel

@Composable
fun PasswordThreeScreen(
    navController: NavController? = null,
    onBackClick: () -> Unit = {},
    viewModel: PasswordRecoveryViewModel = viewModel()
) {
    val newPassword = viewModel.newPassword.collectAsState().value
    val confirmPassword = viewModel.confirmPassword.collectAsState().value
    val uiState = viewModel.uiState.collectAsState().value
    var showError by remember { mutableStateOf<String?>(null) }
    var showSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        when (uiState) {
            is PasswordRecoveryViewModel.UiState.PasswordChanged -> {
                showSuccess = true
                viewModel.resetState()
            }
            is PasswordRecoveryViewModel.UiState.Error -> {
                showError = uiState.message
            }
            else -> {}
        }
    }
    if (showSuccess) {
        // Mostrar mensaje y navegar a login
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(1500)
            navController?.navigate("Login_screen")
        }
        Text("Contraseña cambiada con éxito", color = Color.Green, modifier = Modifier.padding(16.dp))
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
        Spacer(modifier = Modifier.height(20.dp))

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

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "RECUPERACIÓN DE CONTRASEÑA",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Establece tu nueva contraseña",
            fontSize = 12.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newPassword,
            onValueChange = { viewModel.newPassword.value = it },
            placeholder = { Text("Ingrese la nueva contraseña") },
            label = { Text("Contraseña nueva") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Nueva contraseña"
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { viewModel.confirmPassword.value = it },
            placeholder = { Text("Confirme la nueva contraseña") },
            label = { Text("Confirmar contraseña nueva") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Confirmar contraseña"
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedButton(
            onClick = { viewModel.changePassword() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(50.dp),
            border = BorderStroke(1.dp, Color.White),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFFB00020),
                containerColor = Color.White
            )
        ) {
            Text(
                text = "CONFIRMAR",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (showError != null) {
            Text(showError!!, color = Color.Yellow, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Preview(
    showBackground = true,
    name = "Password Change Screen",
    device = "id:pixel_4",
    showSystemUi = true
)
@Composable
fun PasswordThreeScreenPreview() {
    UvMatchTheme {
        PasswordThreeScreen()
    }
}