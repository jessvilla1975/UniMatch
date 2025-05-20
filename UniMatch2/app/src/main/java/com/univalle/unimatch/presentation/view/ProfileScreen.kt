package com.univalle.unimatch.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.univalle.unimatch.ui.theme.UvMatchTheme

@Composable
fun ProfileScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB00000)) // Rojo fondo
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Espacio superior aún mayor para bajar más todo el contenido
            Spacer(modifier = Modifier.height(90.dp))

            // Iconos superiores
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                RoundedIcon("ic_user", 46.dp, 20.dp)
                RoundedIcon("ic_flame", 46.dp, 20.dp)
                RoundedIcon("ic_chat", 46.dp, 20.dp)
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Foto circular más grande

            Box(
                modifier = Modifier
                    .size(220.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )

            //Descomentar para poner foto
            /*
             Box(
                modifier = Modifier
                    .size(220.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.foto), // nombre de tu imagen SIN extensión
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
             */


            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Carlos Camacho, 22",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "Co-Fundador y CEO\nBusco mona creída y belicosa.",
                fontSize = 15.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Botones inferiores en forma de V
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                // Botón de configuración (izquierda)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    RoundedIcon("ic_settings", 50.dp, 24.dp)
                }

                // Botón de cámara (centro y más abajo)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    RoundedIcon("ic_camera", 75.dp, 38.dp)
                }

                // Botón de editar (derecha)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    RoundedIcon("ic_edit", 50.dp, 24.dp)
                }
            }

            // Espacio flexible para empujar el contenido hacia arriba
            Spacer(modifier = Modifier.weight(1f))
        }

        // Botón de salir - mantenido en la posición anterior
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 16.dp, bottom = 100.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .clickable { /* Acción salir */ },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logout),
                    contentDescription = "Salir",
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}

@Composable
fun RoundedIcon(drawableName: String, size: androidx.compose.ui.unit.Dp = 56.dp, iconSize: androidx.compose.ui.unit.Dp = 24.dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.White)
            .clickable { /* Acción del botón */ },
        contentAlignment = Alignment.Center
    ) {
        val iconId = when (drawableName) {
            "ic_user" -> R.drawable.ic_user
            "ic_flame" -> R.drawable.ic_flame
            "ic_chat" -> R.drawable.ic_chat
            "ic_settings" -> R.drawable.ic_settings
            "ic_camera" -> R.drawable.ic_camera
            "ic_edit" -> R.drawable.ic_edit
            else -> R.drawable.ic_launcher_foreground
        }

        Image(
            painter = painterResource(id = iconId),
            contentDescription = drawableName,
            modifier = Modifier.size(iconSize)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    UvMatchTheme {
        ProfileScreen(navController = rememberNavController())
    }
}