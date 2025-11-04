package com.agus.wellnessapp.ui.navigation

import android.util.Log

private const val TAG = "Navigation"

sealed class Screen(val route: String) {

    object List : Screen("list") {
        fun log() {
            Log.d(TAG, "Navigating to: $route")
        }
    }

    object Detail : Screen("detail/{sessionId}") {

        const val ARG_SESSION_ID = "sessionId"

        fun createRoute(sessionId: Int): String {
            val route = "detail/$sessionId"
            Log.d(TAG, "Creating route: $route")
            return route
        }
    }
}