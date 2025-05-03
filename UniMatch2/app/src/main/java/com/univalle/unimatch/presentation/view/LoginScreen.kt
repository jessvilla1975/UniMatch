package com.univalle.unimatch.presentation.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.univalle.unimatch.R
import com.univalle.unimatch.ui.theme.UvMatchTheme


@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCD1F32)), // Rojo de fondo
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())// Agregar Scroll
        ) {
            // Titulo
            Text(
                text = "UNI MATCH",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.logo), // Reemplaza "logo" con el nombre de tu imagen en drawable
                contentDescription = "Logo de Uni Match",
                modifier = Modifier
                    .size(150.dp) // Ajusta el tamaño del logo
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Campos de entrada
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label = { Text("Email", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    focusedTextColor = Color.White,   // Color del texto cuando el campo está enfocado
                    unfocusedTextColor = Color.White  // Color del texto cuando el campo NO está enfocado
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    focusedTextColor = Color.White,   // Color del texto cuando el campo está enfocado
                    unfocusedTextColor = Color.White  // Color del texto cuando el campo NO está enfocado
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de Iniciar Sesión
            Button(
                onClick = { /* Lógica de login */ },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp), // Esquinas redondeadas
                border = BorderStroke(2.dp, Color.White) // Borde blanco
            ) {
                Text("INICIAR SESIÓN", color = Color.Red , fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Olvidaste tu contraseña
            Text(
                text = "¿OLVIDASTE TU CONTRASEÑA?",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.clickable { /* Navegar a recuperación */ }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botones de Facebook y Google
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { /* Login con Facebook */ }) {
                    Image(painterResource(id = R.drawable.ic_facebook), contentDescription = "Facebook")
                }
                Spacer(modifier = Modifier.width(32.dp))
                IconButton(onClick = { /* Login con Google */ }) {
                    Image(painterResource(id = R.drawable.ic_google), contentDescription = "Google")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Registro
            Row {
                Text(text = "¿No tienes cuenta?", color = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Regístrate.",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline, // Subrayado
                    modifier = Modifier.clickable {
                        navController.navigate("register_screen") // Navega a la pantalla de registro
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    UvMatchTheme {
        LoginScreen(navController = rememberNavController())
    }
}