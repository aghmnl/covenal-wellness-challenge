package com.agus.wellnessapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.agus.wellnessapp.ui.detail.SessionDetailScreen
import com.agus.wellnessapp.ui.list.SessionListScreen
import com.agus.wellnessapp.ui.navigation.Screen
import com.agus.wellnessapp.ui.theme.CovenalWellnessAppTheme
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Activity creating...")
        enableEdgeToEdge()
        setContent {
            CovenalWellnessAppTheme {
                val navController = rememberNavController()
                Log.d(TAG, "onCreate: Setting up NavHost")

                NavHost(
                    navController = navController,
                    startDestination = Screen.List.route
                ) {
                    composable(route = Screen.List.route) {
                        Log.d(TAG, "NavHost: Composing ListScreen")
                        SessionListScreen(
                            onSessionClick = { sessionId ->
                                Log.d(TAG, "NavHost: List item clicked, id: $sessionId")
                                navController.navigate(Screen.Detail.createRoute(sessionId))
                            }
                        )
                    }

                    composable(route = Screen.Detail.route) {
                        Log.d(TAG, "NavHost: Composing DetailScreen")
                        SessionDetailScreen(navController = navController)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Activity started.")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Activity resumed.")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Activity paused.")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Activity stopped.")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Activity destroyed.")
    }
}