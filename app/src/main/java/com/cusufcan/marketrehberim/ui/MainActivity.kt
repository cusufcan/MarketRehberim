package com.cusufcan.marketrehberim.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.cusufcan.marketrehberim.navigation.NavigationGraph
import com.cusufcan.marketrehberim.navigation.Screen.Home
import com.cusufcan.marketrehberim.ui.theme.MyappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyappTheme {
                val navController = rememberNavController()
                val startDestination = Home
                NavigationGraph(
                    navController = navController,
                    startDestination = startDestination,
                )
            }
        }
    }
}