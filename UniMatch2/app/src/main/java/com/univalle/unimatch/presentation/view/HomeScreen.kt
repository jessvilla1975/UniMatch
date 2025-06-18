package com.univalle.unimatch.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.univalle.unimatch.ui.theme.UvMatchTheme

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagen principal del perfil
        AsyncImage(
            model = "https://i.pinimg.com/1200x/2d/ac/94/2dac94ced0370e8ca26d9756eccff0ed.jpg",
            contentDescription = "Imagen de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Degradado inferior para texto legible
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                        startY = 300f
                    )
                )
        )

        // Contenido (texto y botones)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nombre y edad
            Text(
                text = "Mariana Zapata, 23",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            // Universidad
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
            ) {
                AsyncImage(
                    model = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/WOmSfnUytF/ra48qhum_expires_30_days.png",
                    contentDescription = "Icono ubicación",
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    text = "Univalle Tuluá",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            // Botones de acción
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                val buttonSize = 60.dp

                IconButton(onClick = { /* Rewind */ }) {
                    AsyncImage(
                        model = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/WOmSfnUytF/hzez4bz5_expires_30_days.png",
                        contentDescription = "Rewind",
                        modifier = Modifier.size(buttonSize)
                    )
                }

                IconButton(onClick = { /* Dislike */ },
                    modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFFED4949), shape = CircleShape)) {
                    AsyncImage(
                        model = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/WOmSfnUytF/w65cxr86_expires_30_days.png",
                        contentDescription = "Dislike",
                        modifier = Modifier.size(buttonSize)
                    )
                }

                IconButton(onClick = { /* Like */ }) {
                    AsyncImage(
                        model = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/WOmSfnUytF/mfiplr2f_expires_30_days.png",
                        contentDescription = "Like",
                        modifier = Modifier.size(buttonSize)
                    )
                }

                IconButton(onClick = { /* Super Like */ }) {
                    AsyncImage(
                        model = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/WOmSfnUytF/2ll4ijk6_expires_30_days.png",
                        contentDescription = "Super Like",
                        modifier = Modifier.size(buttonSize)
                    )
                }
            }
        }

        // Top icons (barra superior)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 70.dp, end = 70.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón: Perfil
            IconButton(
                onClick = { navController.navigate("Profile_screen") },
                modifier = Modifier
                    .size(70.dp)
            ) {
                AsyncImage(
                    model = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/WOmSfnUytF/0yh5whgv_expires_30_days.png",
                    contentDescription = "Perfil",
                    modifier = Modifier.size(50.dp)
                )
            }

            // Botón: Logo central (sin fondo)
            AsyncImage(
                model = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/WOmSfnUytF/2tpqvq7u_expires_30_days.png",
                contentDescription = "Logo",
                modifier = Modifier.size(80.dp).background(Color(0xFFED4949), shape = CircleShape)
            )

            // Botón: Chat
            IconButton(
                onClick = { navController.navigate("Chat_screen") },
                modifier = Modifier
                    .size(70.dp)
            ) {
                AsyncImage(
                    model = "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/WOmSfnUytF/wqcbuq4f_expires_30_days.png",
                    contentDescription = "Chat",
                    modifier = Modifier.size(50.dp)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    UvMatchTheme {
        HomeScreen(navController = rememberNavController())
    }
}