package com.agus.wellnessapp.ui.list

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

private const val TAG = "SessionListScreen"

@Composable
fun SessionListScreen(
    viewModel: SessionListViewModel = hiltViewModel(),
    onSessionClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()

    Log.d(TAG, "Composing with state: isLoading=${uiState.isLoading}, errors=${uiState.error}, sessions=${uiState.sessions.size}")
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

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
                Log.d(TAG, "Displaying: Error - $error")
                Text(
                    text = error,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }


            if (uiState.sessions.isNotEmpty()) {
                Log.d(TAG, "Displaying: Session List with ${uiState.sessions.size} items")
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(uiState.sessions, key = { it.id }) { pose ->
                        val isFavorite = favoriteIds.contains(pose.id.toString())
                        SessionListItem(
                            pose = pose,
                            isFavorite = isFavorite,
                            onItemClick = onSessionClick,
                            onFavoriteClick = {
                                Log.d(TAG, "Favorite icon clicked for id: ${pose.id}")
                                viewModel.onToggleFavorite(pose.id.toString())
                            }
                        )
                    }
                }
            }
        }
    }
}