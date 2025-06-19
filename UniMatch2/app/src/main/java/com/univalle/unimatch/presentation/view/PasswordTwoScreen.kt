package com.univalle.unimatch.presentation.view

import androidx.compose.foundation.Image
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
fun PasswordTwoScreen(
    navController: NavController,
    onBackClick: () -> Unit = {},
    viewModel: PasswordRecoveryViewModel = viewModel()
) {
    val code = viewModel.code.collectAsState().value
    val uiState = viewModel.uiState.collectAsState().value
    var showError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(uiState) {
        when (uiState) {
            is PasswordRecoveryViewModel.UiState.CodeValidated -> {
                navController.navigate("PasswordThree_screen")
                viewModel.resetState()
            }
            is PasswordRecoveryViewModel.UiState.Error -> {
                showError = uiState.message
            }
            else -> {}
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
            text = "Ingresa el código que enviamos a tu correo",
            fontSize = 12.sp,
            color = Color.White,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = code,
            onValueChange = { viewModel.code.value = it },
            placeholder = { Text("••••••") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Código"
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.validateCode() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFFB00020))
        ) {
            Text(
                text = "RECUPERAR CONTRASEÑA",
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
    name = "Password Code Screen",
    device = "id:pixel_4",
    showSystemUi = true
)
@Composable
fun PasswordTwoScreenPreview() {
    UvMatchTheme {
        PasswordTwoScreen(navController = rememberNavController())
    }
}