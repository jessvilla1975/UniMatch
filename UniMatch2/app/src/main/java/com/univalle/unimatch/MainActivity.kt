package com.univalle.unimatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.univalle.unimatch.ui.theme.UvMatchTheme
import com.univalle.unimatch.presentation.view.LoginScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UvMatchTheme (){
                val navController = rememberNavController() // Inicializamos NavController
                Scaffold (modifier = Modifier.fillMaxSize()){ innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login_screen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login_screen") { LoginScreen(navController) }
                    }

                }
            }
        }
    }
}