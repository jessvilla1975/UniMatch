package com.univalle.unimatch.presentation.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.animation.core.animateFloatAsState
import com.univalle.unimatch.ui.theme.UvMatchTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush


@Composable
fun InterestsScreen(navController: NavController) {
    val maxSelection = 5
    val intereses = listOf(
        "Cine",
        "Harry Potter",
        "Bicicleta",
        "Corridos",
        "Running",
        "Spa",
        "Techno",
        "Gastronomia",
        "Viajes",
        "Voleyball",
        "Gimnasia",
        "Videojuegos",
        "Moda",
        "Meditacion",
        "Perros",
        "Sushi",
        "Fútbol",
        "Tenis",
        "Teatro",
        "Basketball",
        "Poesía",
        "Motocicletas",
        "Natación",
        "Rock",
        "Instagram",
        "Pop",
        "Termales",
        "Caminatas"
    )
    val selectedIntereses = remember { mutableStateListOf<String>() }
    val progress by animateFloatAsState(
        targetValue = selectedIntereses.size / maxSelection.toFloat()
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFB30811), Color(0xFF4D0307))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }
            }
            // Barra de progreso
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(50)),
                color = Color.White,
                trackColor = Color.White.copy(alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Intereses",
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Deja que los demás sepan que puede interesarte, añadiendolo en tu perfil.",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.9f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                intereses.forEach { interes ->
                    val isSelected = selectedIntereses.contains(interes)
                    Box(
                        modifier = Modifier
                            .padding(4.dp) // espacio entre elementos
                            .border(
                                BorderStroke(1.dp, Color.White),
                                shape = RoundedCornerShape(50)
                            )
                            .background(
                                color = if (isSelected) Color.White else Color.Transparent,
                                shape = RoundedCornerShape(50)
                            )
                            .clickable {
                                if (isSelected) {
                                    selectedIntereses.remove(interes)
                                } else if (selectedIntereses.size < maxSelection) {
                                    selectedIntereses.add(interes)
                                }
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = interes,
                            color = if (isSelected) Color.Black else Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate("photo_upload_screen")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(50),
                enabled = selectedIntereses.isNotEmpty() // Habilitar solo si hay al menos 1 seleccionado
            ) {
                Text(
                    text = "CONTINUAR (${selectedIntereses.size}/$maxSelection)",
                    color = Color(0xFFADA0A0),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun InterestsScreenPreview() {
    UvMatchTheme {
        InterestsScreen(navController = rememberNavController())
    }
}


