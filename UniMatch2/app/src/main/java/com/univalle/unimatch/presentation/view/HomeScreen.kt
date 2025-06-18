package com.univalle.unimatch.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.univalle.unimatch.R
import com.univalle.unimatch.ui.theme.UvMatchTheme

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    )   {
        Image (
            painter = painterResource(id = R.drawable.imagentemporal),
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
                Image(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "Icono ubicación",
                    modifier = Modifier
                        .size(35.dp)
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

                IconButton(onClick = { /* Rewind */ },
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color(0xFFDF0814), shape = CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_rewind),
                        contentDescription = "Rewind",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(45.dp)
                    )
                }

                IconButton(onClick = { /* Dislike */ },
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color(0xFFDF0814), shape = CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_dislike),
                        contentDescription = "Dislike",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(45.dp)
                    )
                }

                IconButton(onClick = { /* Like */ },
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color(0xFFDF0814), shape = CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_like),
                        contentDescription = "Like",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(45.dp)
                    )
                }

                IconButton(onClick = { /* Super Like */ },
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color(0xFFDF0814), shape = CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_superlike),
                        contentDescription = "SuperLike",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(45.dp)
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
                Image(
                    painter = painterResource(id = R.drawable.ic_user_white),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.
                    size(60.dp).background(Color(0xFFDF0814), shape = CircleShape)
                )
            }

            // Botón: Logo central (sin fondo)
            Image(
                painter = painterResource(id = R.drawable.ic_uvmatch),
                contentDescription = "Home",
                contentScale = ContentScale.Crop,
                modifier = Modifier.
                size(90.dp).background(Color(0xFFDF0814), shape = CircleShape)
            )

            // Botón: Chat
            IconButton(
                onClick = { navController.navigate("Chat_screen") },
                modifier = Modifier
                    .size(70.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_chat_white),
                    contentDescription = "Chat",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp).background(Color(0xFFDF0814), shape = CircleShape)
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