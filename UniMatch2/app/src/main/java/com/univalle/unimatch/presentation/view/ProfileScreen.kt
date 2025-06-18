import androidx.compose.runtime.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.univalle.unimatch.R
import com.univalle.unimatch.ui.theme.UvMatchTheme

@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color(0xFFFFFFFF),
            )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFFB30811), Color(0xFF000000)),
                        start = Offset.Zero,
                        end = Offset(0f, Float.POSITIVE_INFINITY)
                    )
                )
                .verticalScroll(rememberScrollState())
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 53.dp,bottom = 43.dp,)
                    .fillMaxWidth()
            ){
                // Top icons (barra superior)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 70.dp, end = 70.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón: Perfil
                    Image(
                        painter = painterResource(id = R.drawable.ic_user_white),
                        contentDescription = "Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.
                        size(80.dp).background(Color(0xFFDF0814), shape = CircleShape)
                    )

                    // Botón: Logo central
                    IconButton(
                        onClick = { navController.navigate("Home_screen") },
                        modifier = Modifier
                            .size(65.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_uvmatch),
                            contentDescription = "Home",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.
                            size(55.dp).background(Color(0xFFDF0814), shape = CircleShape)
                        )
                    }

                    // Botón: Chat
                    IconButton(
                        onClick = { navController.navigate("Chat_screen") },
                        modifier = Modifier
                            .size(65.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_chat_white),
                            contentDescription = "Chat",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(55.dp).background(Color(0xFFDF0814), shape = CircleShape)
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top=15.dp, bottom = 10.dp)
                        .fillMaxWidth()
                ){
                    Image(
                        painter = painterResource(id = R.drawable.perfil),
                        contentDescription = "Imagen de perfil local",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(250.dp) // usa size para ancho y alto iguales
                            .clip(CircleShape)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top=10.dp, bottom = 5.dp)
                        .fillMaxWidth()
                ){
                    Text("Carlos Camacho, 22",
                        color = Color(0xFFFFFFFF),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(bottom = 53.dp,)
                        .fillMaxWidth()
                ){
                    Text("Co-Fundador y CEO\nBusco mona creída y belicosa.",
                        color = Color(0xFFFFFFFF),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(295.dp)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(bottom = 40.dp,)
                        .fillMaxWidth()
                ){
                    Row(
                    ){
                        IconButton(
                            onClick = { /* Settings */ },
                            modifier = Modifier
                                .padding(end=40.dp)
                                .size(80.dp)
                                .border(width = 1.dp, color = Color.Red, shape = CircleShape)
                                .padding(12.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_settings),
                                contentDescription = "Settings",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(65.dp)
                            )
                        }
                        IconButton(
                            onClick = { /* Camera */ },
                            modifier = Modifier
                                .padding(top=40.dp, end=40.dp)
                                .size(80.dp)
                                .border(width = 1.dp, color = Color.Red, shape = CircleShape)
                                .padding(12.dp)

                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_camera),
                                contentDescription = "Camera",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(70.dp)

                            )
                        }
                        IconButton(
                            onClick = { /* Edit */ },
                            modifier = Modifier
                                .size(80.dp)
                                .border(width = 1.dp, color = Color.Red, shape = CircleShape)
                                .padding(12.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_edit),
                                contentDescription = "Edit",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(70.dp)
                            )
                        }
                    }
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .padding(bottom = 23.dp,)
                        .fillMaxWidth()
                ){
                    Image(
                        painter = painterResource(id = R.drawable.ic_logout_white),
                        contentDescription = "Logout",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(end = 10.dp,)
                            .width(57.dp)
                            .height(57.dp)
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    UvMatchTheme {
        ProfileScreen(navController = rememberNavController())
    }
}