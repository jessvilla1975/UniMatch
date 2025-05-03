package com.univalle.unimatch.presentation.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.univalle.unimatch.R
import com.univalle.unimatch.presentation.view.components.CustomOutlinedTextField
import com.univalle.unimatch.presentation.viewmodel.RegisterViewModel
import com.univalle.unimatch.ui.theme.UvMatchTheme

@Composable
fun RegisterScreen (navController: NavController){
    val viewModel: RegisterViewModel = viewModel() // Logica del registro del usuario.
    val selectedGender = viewModel.selectedGender
    val genders = listOf("Masculino", "Femenino", "Otro")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCD1F32)) // Color de Fondo de la Apliación
            .verticalScroll(rememberScrollState()), // Permite hacer scroll si hay mucchos campos
        contentAlignment = Alignment.TopCenter
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Icono de regreso
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(1.dp))

                // Imagen del Logo de la Apliación
                Image(
                    painter = painterResource(id = R.drawable.logo), // Reemplaza "logo" con el nombre de tu imagen en drawable
                    contentDescription = "Logo de Uni Match",
                    modifier = Modifier
                        .size(80.dp) // Ajusta el tamaño del logo
                )
                Spacer(modifier = Modifier.width(1.dp))
                Text(
                    text = "UNI MATCH",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "REGISTRO",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campos de Texto o del formulario
            CustomOutlinedTextField("Nombre Cuenta", viewModel.nombreCuenta) {viewModel.nombreCuenta = it }
            CustomOutlinedTextField("Nombre", viewModel.nombre) { viewModel.nombre = it }
            CustomOutlinedTextField("Apellido", viewModel.apellido) { viewModel.apellido = it }
            CustomOutlinedTextField("Correo Electrónico", viewModel.email) { viewModel.email = it }
            CustomOutlinedTextField("Contraseña", viewModel.password, true) { viewModel.password = it }
            CustomOutlinedTextField("Confirmar Contraseña", viewModel.confirmPassword, true) { viewModel.confirmPassword = it }
            CustomOutlinedTextField("Número de Teléfono", viewModel.phoneNumber) { viewModel.phoneNumber = it }
            CustomOutlinedTextField("Fecha de Nacimiento", viewModel.birthDate) { viewModel.birthDate = it }

            Spacer(modifier = Modifier.height(8.dp))

            // Selector de Género
            Text(text = "Género", color = Color.White, fontSize = 16.sp)
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                genders.forEach { gender ->
                    Button(
                        onClick = { viewModel.selectedGender = gender },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedGender == gender) Color.White else Color.Gray
                        )
                    ) {
                        Text(text = gender, color = if (selectedGender == gender) Color.Red else Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de registro
            Button(
                onClick = {/* TODO: Logica para validar los campos */},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp), // Esquinas redondeadas
                border = BorderStroke(2.dp, Color.White) // Borde blanco
            ) {
                Text("REGISTRARSE", color = Color.Red ,fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    UvMatchTheme {
        RegisterScreen(navController = rememberNavController())
    }
}