package com.univalle.unimatch.presentation.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.univalle.unimatch.R
import com.univalle.unimatch.ui.theme.UvMatchTheme

@Composable
fun VerifyAccountScreen (navController: NavController){
    var verificationCode by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCD1F32)), // Color de Fondo
        contentAlignment = Alignment.TopCenter
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Icono de regreso
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
            Spacer(modifier = Modifier.height(16.dp))

            // Título
            Text(
                text = "VERIFICA TU CUENTA",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Subtítulo
            Text(
                text = "INGRESA AQUÍ EL CÓDIGO QUE SE ENVIÓ A TU CORREO",
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Código de Verificación
            OutlinedTextField(
                value = verificationCode,
                onValueChange = { verificationCode = it },
                label = { Text("Código de verificación", color = Color.White) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock, // Ícono que se muestra al inicio del campo
                        contentDescription = "Ícono de código",
                        tint = Color.White
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(), // Para ocultar el código
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

            // Botón de Verificar Cuenta
            Button(
                onClick = { /* Lógica de verificación */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                border = BorderStroke(2.dp, Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("VERIFICAR CUENTA", color = Color.Red, fontWeight = FontWeight.Bold)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun VerifyAccountScreenPreview() {
    UvMatchTheme {
        VerifyAccountScreen(navController = rememberNavController())
    }
}
