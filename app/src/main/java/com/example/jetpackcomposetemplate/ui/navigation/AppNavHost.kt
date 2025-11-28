package com.example.jetpackcomposetemplate.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class AppRoute(val route: String) {
    object Home : AppRoute("home")
    object Coroutines : AppRoute("coroutines")
    object Threads : AppRoute("threads")
    object WorkManager : AppRoute("workmanager")
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.Home.route
    ) {

        composable(AppRoute.Home.route) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Text("Pantalla Home (placeholder)")
            }
        }

        composable(AppRoute.Coroutines.route) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Text("Pantalla Corrutinas (placeholder)")
            }
        }

        composable(AppRoute.Threads.route) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Text("Pantalla Threads (placeholder)")
            }
        }

        composable(AppRoute.WorkManager.route) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Text("Pantalla WorkManager (placeholder)")
            }
        }
    }
}
