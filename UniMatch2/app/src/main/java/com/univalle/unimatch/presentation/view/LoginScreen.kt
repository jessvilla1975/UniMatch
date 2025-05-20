package com.univalle.unimatch.presentation.view

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.univalle.unimatch.R
import com.univalle.unimatch.presentation.viewmodel.LoginViewModel
import com.univalle.unimatch.ui.theme.UvMatchTheme
import kotlinx.coroutines.launch
import com.univalle.unimatch.data.repository.GoogleAuthClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel? = null,
    showSnackbars: Boolean = true // Para desactivar en previews
) {
    val viewModel = loginViewModel ?: viewModel()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) } // Para el estado del icono de mostrar/ocultar Contraseña
    val loginViewModel: LoginViewModel = viewModel()
    val loginResult by loginViewModel.loginResult.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val activity = context as Activity

    val googleAuthClient = remember { GoogleAuthClient(context) }

    val googleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        if (result.resultCode == Activity.RESULT_OK && data != null) {
            coroutineScope.launch {
                val user = googleAuthClient.signInWithGoogle(data)
                if (user != null) {
                    Log.d("Login", "Usuario logueado: ${user.email}")
                    navController.navigate("home_screen") {
                        popUpTo("login_screen") { inclusive = true }
                    }
                } else {
                    snackbarHostState.showSnackbar("Error al iniciar sesión con Google")
                }
            }
        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Inicio de sesión cancelado")
            }
        }
    }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {  innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFCD1F32)) // Rojo de fondo
                .padding(innerPadding),
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
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email, // Ícono que se muestra al inicio del campo
                            contentDescription = "Ícono de Candado",
                            tint = Color.White
                        )
                    },
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
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock, // Ícono que se muestra al inicio del campo
                            contentDescription = "Ícono de Candado",
                            tint = Color.White
                        )
                    },
                    trailingIcon = {
                        val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = icon,
                                contentDescription = description,
                                tint = Color.White
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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

                Spacer(modifier = Modifier.height(16.dp))

                // Botón de Iniciar Sesión
                Button(
                    onClick = {
                        loginViewModel.loginUser(email, password)
                    },
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

                // Boton de Google
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = {
                        val signInIntent = googleAuthClient.getSignInIntent()
                        googleLauncher.launch(signInIntent)
                }) {
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
            LaunchedEffect(loginResult) {
                loginResult?.let {
                    it.onSuccess {
                        navController.navigate("home_screen") {
                            popUpTo("login_screen") { inclusive = true }
                        }
                    }.onFailure {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Correo o contraseña incorrectos")
                        }
                    }
                    loginViewModel.resetLoginResult()
                }
            }
        }
    }
}


