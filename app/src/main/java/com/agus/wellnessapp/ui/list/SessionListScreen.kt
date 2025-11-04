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
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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

        topBar = {
            TopAppBar(
                title = {
                    Text("Wellness Sessions")
                },
                actions = {
                    IconButton(onClick = { }) {
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
                                contentDescription = "Favorite count"
                            )
                        }
                    }
                }
            )
        }

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
                Log.e(TAG, "Displaying FATAL error: $error")
                ErrorStateView(message = error, modifier = Modifier.fillMaxSize())
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
                            },
                            backgroundColor = AppCardColors.background,
                            titleColor = AppCardColors.title,
                            bodyColor = AppCardColors.body
                        )
                    }
                }
            }
        }
    }
}