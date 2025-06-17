package com.univalle.unimatch.presentation.view

import android.annotation.SuppressLint
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
import androidx.compose.foundation.Image
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.painterResource
import com.univalle.unimatch.R
import com.univalle.unimatch.ui.theme.UvMatchTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreen(navController: NavController) {
    Scaffold{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFFB30811), Color(0xFF000000)),
                            start = Offset.Zero,
                            end = Offset(0f, Float.POSITIVE_INFINITY)
                        )
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.header),
                        contentDescription = "Header",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

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
                                modifier = Modifier.size(50.dp)
                            )
                        }
                        // Botón: Inicio
                        IconButton(
                            onClick = { navController.navigate("Home_screen") },
                            modifier = Modifier
                                .size(80.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_uvmatch),
                                contentDescription = "Home",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(60.dp)
                            )
                        }
                        // Botón: Chat
                        Image(
                            painter = painterResource(id = R.drawable.ic_chat_white),
                            contentDescription = "Chats",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(70.dp).background(Color(0xFFED4949), shape = CircleShape)
                        )
                    }
                }
                ChatListSection()
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logout_white),
                        contentDescription = "btn_logout",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(40.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun ChatListSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
    ) {
        ChatItem("Mariana Zapata", "Como amaneciste?", "Hace 1 minuto", "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/WOmSfnUytF/fk7r2o9v_expires_30_days.png", 200)
        ChatItem("Karina Garcia", "Buenos días", "Hace 1 hora", "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/WOmSfnUytF/52582mfh_expires_30_days.png", 200)
        ChatItem("Melina Ramirez", "Nos vemos hoy?", "Hace 1 día", "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/WOmSfnUytF/r42v11ty_expires_30_days.png", 200)
        ChatItem("Sara Orrego", "Te ví en la cafetería", "Hace 2 días", "https://storage.googleapis.com/tagjs-prod.appspot.com/v1/WOmSfnUytF/jktyhxts_expires_30_days.png", 200)
    }
}

@Composable
fun ChatItem(nombre: String, mensaje: String, tiempo: String, imagenUrl: String, nombreWidth: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 47.dp)
    ) {
        CoilImage(
            imageModel = { imagenUrl },
            imageOptions = ImageOptions(contentScale = ContentScale.Crop),
            modifier = Modifier
                .padding(end = 29.dp)
                .size(110.dp, 110.dp)
        )
        Column(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Text(nombre, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(nombreWidth.dp).padding(bottom = 2.dp))
            Text(mensaje, color = Color(0xFFD0CFCF), fontSize = 15.sp, modifier = Modifier.padding(bottom = 6.dp))
            Text(tiempo, color = Color.White, fontSize = 13.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    UvMatchTheme {
        ChatScreen(navController = rememberNavController())
    }
}