package com.univalle.unimatch

import ProfileScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.univalle.unimatch.presentation.view.AddPhotosScreen
import com.univalle.unimatch.presentation.view.ChatScreen
import com.univalle.unimatch.presentation.view.HomeScreen
import com.univalle.unimatch.presentation.view.InterestsScreen
import com.univalle.unimatch.ui.theme.UvMatchTheme
import com.univalle.unimatch.presentation.view.LoginScreen
import com.univalle.unimatch.presentation.view.RegisterScreen
import com.univalle.unimatch.presentation.view.VerifyAccountScreen
import com.univalle.unimatch.presentation.view.PhotoUploadScreen
import com.univalle.unimatch.presentation.view.WelcomeScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UvMatchTheme (){
                val navController = rememberNavController() // Inicializamos NavController
                Scaffold (modifier = Modifier.fillMaxSize()){ innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "Login_screen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("Login_screen") { LoginScreen(navController) }
                        composable("Register_screen") { RegisterScreen(navController) }
                        composable("VerifyAccount_screen") { VerifyAccountScreen(navController) }
                        composable("Home_screen") {HomeScreen(navController)}
                        composable("Welcome_screen") { WelcomeScreen(navController) }
                        composable("Interests_screen") { InterestsScreen(navController)}
                        composable("Add_photos_screen") { AddPhotosScreen(navController) }
                        composable("Profile_screen") { ProfileScreen(navController) }
                        composable("Chat_screen") { ChatScreen(navController) }
			            composable("photo_upload_screen") { PhotoUploadScreen(navController = navController) }
                    }
                }
            }
        }
    }
