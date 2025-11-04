package com.agus.wellnessapp.ui.list

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agus.wellnessapp.ui.common.EmptyStateView
import com.agus.wellnessapp.ui.common.ErrorStateView
import com.agus.wellnessapp.ui.theme.AppCardColors

private const val TAG = "SessionListScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionListScreen(
    viewModel: SessionListViewModel = hiltViewModel(),
    onSessionClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()
    val cardColors = AppCardColors

    var selectedTab by rememberSaveable { mutableStateOf(SessionTab.ALL) }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.networkErrors.collect { message ->
            Log.d(TAG, "Showing Toast: $message")
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Log.d(TAG, "Composing with state: isLoading=${uiState.isLoading}, sessions=${uiState.sessions.size}, favorites=${favoriteIds.size}")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == SessionTab.ALL,
                    onClick = {
                        Log.d(TAG, "Tab changed: ALL")
                        selectedTab = SessionTab.ALL
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "All Sessions"
                        )
                    },
                    label = { Text("All") }
                )

                NavigationBarItem(
                    selected = selectedTab == SessionTab.FAVORITES,
                    onClick = {
                        Log.d(TAG, "Tab changed: FAVORITES")
                        selectedTab = SessionTab.FAVORITES
                    },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (favoriteIds.isNotEmpty()) {
                                    Badge {
                                        Text(favoriteIds.size.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Favorites"
                            )
                        }
                    },
                    label = { Text("Favorites") }
                )
            }
        }
    ) { innerPadding ->
        val displayedSessions = remember(uiState.sessions, favoriteIds, selectedTab) {
            when (selectedTab) {
                SessionTab.ALL -> uiState.sessions
                SessionTab.FAVORITES -> uiState.sessions.filter {
                    favoriteIds.contains(it.id.toString())
                }
            }
        }

        Log.d(TAG, "Recomposing. Displaying ${displayedSessions.size} items for $selectedTab tab.")

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {

            if (uiState.isLoading) {
                Log.d(TAG, "Displaying: Loading")
                CircularProgressIndicator()
            }

            uiState.error?.let { error ->
                Log.e(TAG, "Displaying FATAL error: $error")
                ErrorStateView(message = error, modifier = Modifier.fillMaxSize())
            }

            if (!uiState.isLoading && uiState.error == null) {
                if (displayedSessions.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
                    ) {
                        items(displayedSessions, key = { it.id }) { pose ->

                            val isFavorite = favoriteIds.contains(pose.id.toString())

                            SessionListItem(
                                pose = pose,
                                isFavorite = isFavorite,
                                onItemClick = onSessionClick,
                                onFavoriteClick = {
                                    Log.d(TAG, "Favorite icon clicked for id: ${pose.id}")
                                    viewModel.onToggleFavorite(pose.id.toString())
                                },
                                backgroundColor = cardColors.background,
                                titleColor = cardColors.title,
                                bodyColor = cardColors.body
                            )
                        }
                    }
                } else {
                    when (selectedTab) {
                        SessionTab.ALL ->
                            EmptyStateView(message = "No sessions found.")
                        SessionTab.FAVORITES ->
                            EmptyStateView(message = "You haven't added any favorites yet.")
                    }
                }
            }
        }
    }
}