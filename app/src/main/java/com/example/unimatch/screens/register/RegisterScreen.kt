package com.example.unimatch.screens.register

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unimatch.ui.theme.UniMatchTheme
import androidx.compose.ui.tooling.preview.Preview
import java.util.Calendar

@Composable
fun RegisterScreen() {
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("+57") }
    var birthDate by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }

    // Contexto para el DatePickerDialog
    val context = LocalContext.current

    // Calendario para configurar la fecha inicial del DatePicker
    val calendar = Calendar.getInstance()

    // DatePicker para seleccionar la fecha de nacimiento
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            // Formatea la fecha seleccionada (month + 1 porque Calendar.MONTH inicia en 0)
            birthDate = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "UNI MATCH",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 45.dp)
        )

        Text(
            text = "REGISTRO",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Número de Teléfono") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de fecha de nacimiento con DatePicker
        OutlinedTextField(
            value = birthDate,
            onValueChange = { },
            label = { Text("Fecha de Nacimiento") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    datePickerDialog.show()
                }) {
                    // Puedes añadir un icono aquí si lo deseas, por ejemplo:
                    Text("📅", fontSize = 20.sp)
                }
            }
        )

        Text("Género", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Masculino", "Femenino", "Otro").forEach { gender ->
                Button(
                    onClick = { selectedGender = gender },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedGender == gender) Color.Gray else Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = gender, color = Color.Black)
                }
            }
        }

        Button(
            onClick = { /* Implementar lógica de registro */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "REGISTRARSE")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    UniMatchTheme {
        RegisterScreen()
    }
}
