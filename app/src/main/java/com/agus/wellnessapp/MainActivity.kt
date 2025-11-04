package com.agus.wellnessapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.agus.wellnessapp.ui.list.SessionListScreen
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
                Log.d(TAG, "onCreate: Setting content to SessionListScreen")
                SessionListScreen()
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