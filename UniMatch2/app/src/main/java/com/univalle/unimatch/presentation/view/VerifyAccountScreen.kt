package com.univalle.unimatch.presentation.view

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.univalle.unimatch.R
import com.univalle.unimatch.presentation.viewmodel.VerifyEmailViewModel
import com.univalle.unimatch.ui.theme.UvMatchTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext

@Composable
fun VerifyAccountScreen (
    navController: NavController,
    viewModel: VerifyEmailViewModel = viewModel()
){
    val context = LocalContext.current
    val isLoading = viewModel.isLoading


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
                text = "INGRESA A TU CORREO Y VERIFICA CON EL LINK QUE LLEGO A TU CORREO",
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Botón de Verificar Cuenta
            Button(
                onClick = {
                    viewModel.checkEmailVerified(
                        onVerified = {
                            Toast.makeText(context, "Cuenta verificada exitosamente", Toast.LENGTH_SHORT).show()
                            navController.navigate("Interests_screen") {
                                popUpTo("VerifyAccount_screen") { inclusive = true }
                            }
                        },
                        onNotVerified = {
                            Toast.makeText(context, "Tu correo aún no ha sido verificado", Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                enabled = !isLoading && !viewModel.isVerified, // deshabilitado si está verificado o cargando
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isLoading && !viewModel.isVerified) Color.White else Color.LightGray
                ),
                border = BorderStroke(2.dp, Color.White),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = if (viewModel.isVerified) "CUENTA VERIFICADA" else "VERIFICAR CUENTA",
                    color = if (viewModel.isVerified) Color.Gray else Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 🔄 Loader debajo del botón
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.CenterHorizontally)
                )
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
