package com.example.futebolfav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.navigation.NavHostController
import com.example.futebolfav.screens.ListScreen
import com.example.futebolfav.screens.RegisterScreen
import com.example.futebolfav.screens.StatisticsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigation(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "register",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("register") { RegisterScreen() }
            composable("list") { ListScreen() }
            composable("statistics") { StatisticsScreen() }
        }
    }
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    NavigationBar {
        val currentRoute = navController
            .currentBackStackEntryAsState().value?.destination?.route

        NavigationBarItem(
            icon = { Icon(Icons.Default.Add, contentDescription = "Register") },
            label = { Text("Register") },
            selected = currentRoute == "register",
            onClick = { navController.navigate("register") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "List") },
            label = { Text("List") },
            selected = currentRoute == "list",
            onClick = { navController.navigate("list") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Statistics") }, // TODO: Replace with actual icon
            label = { Text("Statistics") },
            selected = currentRoute == "statistics",
            onClick = { navController.navigate("statistics") }
        )
    }
}