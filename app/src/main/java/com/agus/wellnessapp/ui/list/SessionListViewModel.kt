package com.agus.wellnessapp.ui.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agus.wellnessapp.data.repository.FavoritesRepository
import com.agus.wellnessapp.data.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SessionListViewModel"

@HiltViewModel
class SessionListViewModel @Inject constructor(
    private val repository: SessionRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SessionListUiState())
    val uiState: StateFlow<SessionListUiState> = _uiState.asStateFlow()

    val favoriteIds: StateFlow<Set<String>> = favoritesRepository.getFavoriteIds()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptySet()
        )

    init {
        Log.d(TAG, "init: ViewModel created. Fetching sessions...")
        fetchSessions()
    }

    fun onToggleFavorite(id: String) {
        Log.d(TAG, "onToggleFavorite: Toggling favorite for id=$id")
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(id)
        }
    }

    private fun fetchSessions() {
        Log.d(TAG, "fetchSessions: Starting to fetch sessions.")
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = repository.getSessions()

            result.onSuccess { poses ->
                Log.i(TAG, "fetchSessions: Success. ${poses.size} poses loaded.")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        sessions = poses
                    )
                }
            }.onFailure { throwable ->
                Log.e(TAG, "fetchSessions: Failure.", throwable)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = throwable.message ?: "An unknown error occurred"
                    )
                }
            }
        }
    }
}