package com.takanakonbu.hobbytime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.takanakonbu.hobbytime.ui.screens.hobby.HobbiesScreen
import com.takanakonbu.hobbytime.ui.theme.HobbyTimeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HobbyTimeTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val navigationItems = listOf(
        NavigationItem("hobbies", "趣味", Icons.Default.EmojiEvents),
        NavigationItem("activities", "活動記録", Icons.AutoMirrored.Filled.Assignment),
        NavigationItem("expenses", "出費", Icons.Default.AttachMoney),
        NavigationItem("analysis", "分析", Icons.Default.Analytics),
        NavigationItem("notes", "メモ", Icons.AutoMirrored.Filled.Note)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                navigationItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "hobbies",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("hobbies") {
                HobbiesScreen()
            }
            composable("activities") {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Text("活動記録画面")
                }
            }
            composable("expenses") {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Text("出費画面")
                }
            }
            composable("analysis") {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Text("分析画面")
                }
            }
            composable("notes") {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Text("メモ画面")
                }
            }
        }
    }
}

data class NavigationItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)