package com.univalle.unimatch.presentation.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.univalle.unimatch.R
import com.univalle.unimatch.presentation.view.components.CustomOutlinedTextField
import com.univalle.unimatch.presentation.view.components.DatePickerField
import com.univalle.unimatch.presentation.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(navController: NavController) {
    val viewModel: RegisterViewModel = viewModel()
    val genders = listOf("Masculino", "Femenino", "Otro")
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCD1F32))
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
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

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo de Uni Match",
                    modifier = Modifier.size(80.dp)
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

            Text("REGISTRO", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                label = "Nombre Cuenta",
                value = viewModel.nombreCuenta,
                onValueChange = { viewModel.onNombreCuentaChange(it) },
                leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color.White) },
                errorMessage = viewModel.nombreCuentaError
            )

            CustomOutlinedTextField(
                label = "Nombre",
                value = viewModel.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.White) },
                errorMessage = viewModel.nombreError
            )

            CustomOutlinedTextField(
                label = "Apellido",
                value = viewModel.apellido,
                onValueChange = { viewModel.onApellidoChange(it) },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.White) },
                errorMessage = viewModel.apellidoError
            )

            CustomOutlinedTextField(
                label = "Correo Electrónico",
                value = viewModel.email,
                onValueChange = { viewModel.onEmailChange(it) },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color.White) },
                errorMessage = viewModel.emailError
            )

            CustomOutlinedTextField(
                label = "Contraseña",
                value = viewModel.password,
                isPassword = true,
                onValueChange = { viewModel.onPasswordChange(it) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.White) },
                errorMessage = viewModel.passwordError
            )

            CustomOutlinedTextField(
                label = "Confirmar Contraseña",
                value = viewModel.confirmPassword,
                isPassword = true,
                onValueChange = { viewModel.onConfirmPasswordChange(it) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.White) },
                errorMessage = viewModel.confirmPasswordError
            )

            CustomOutlinedTextField(
                label = "Número de Teléfono",
                value = viewModel.phoneNumber,
                onValueChange = { viewModel.onPhoneNumberChange(it) },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = Color.White) },
                errorMessage = viewModel.phoneNumberError
            )

            DatePickerField(viewModel = viewModel)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Género", color = Color.White, fontSize = 16.sp)
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                genders.forEach { gender ->
                    Button(
                        onClick = { viewModel.selectedGender = gender },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (viewModel.selectedGender == gender) Color.White else Color.Gray
                        )
                    ) {
                        Text(
                            text = gender,
                            color = if (viewModel.selectedGender == gender) Color.Red else Color.White
                        )
                    }
                }
            }
            // Mostrar error si existe
            viewModel.selectedGenderError?.let { error ->
                Text(
                    text = error,
                    color = Color.Yellow,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(Alignment.Start)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.iniciarRegistro(
                        context = context,
                        onSuccess = {
                            navController.navigate("VerifyAccount_screen")
                        },
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(2.dp, Color.White)
            ) {
                Text("REGISTRARSE", color = Color.Red, fontWeight = FontWeight.Bold)
            }
        }
    }
}

