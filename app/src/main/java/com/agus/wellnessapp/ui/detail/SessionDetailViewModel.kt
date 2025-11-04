package com.agus.wellnessapp.ui.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agus.wellnessapp.data.repository.FavoritesRepository
import com.agus.wellnessapp.data.repository.SessionRepository
import com.agus.wellnessapp.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SessionDetailViewModel"

@HiltViewModel
class SessionDetailViewModel @Inject constructor(
    private val repository: SessionRepository,
    private val favoritesRepository: FavoritesRepository,
    savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(SessionDetailUiState())
    val uiState: StateFlow<SessionDetailUiState> = _uiState.asStateFlow()

    val networkErrors: Flow<String> = repository.getNetworkErrors()

    private val sessionId: String? = savedStateHandle.get<String>(Screen.Detail.ARG_SESSION_ID)

    val isFavorite: StateFlow<Boolean> = favoritesRepository.getFavoriteIds()
        .map { favorites ->
            val isFav = favorites.contains(sessionId)
            Log.d(TAG, "isFavorite flow: $isFav for id=$sessionId")
            isFav
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = false
        )

    init {
        Log.d(TAG, "init: ViewModel created, sessionId: $sessionId")

        if (sessionId != null) {
            fetchSessionDetail(sessionId)
        } else {
            Log.e(TAG, "init: Session ID is null!")
            _uiState.update { it.copy(error = "Session ID not found.") }
        }
    }

    fun onToggleFavorite() {
        if (sessionId == null) {
            Log.e(TAG, "onToggleFavorite: Cannot toggle, session ID is null")
            return
        }
        Log.d(TAG, "onToggleFavorite: Toggling favorite for id=$sessionId")
        viewModelScope.launch {
            favoritesRepository.toggleFavorite(sessionId)
        }
    }

    private fun fetchSessionDetail(id: String) {
        Log.d(TAG, "fetchSessionDetail: Fetching details for id=$id")
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = repository.getSessionDetail(id)

            result.onSuccess { pose ->
                Log.i(TAG, "fetchSessionDetail: Success for id=$id")
                _uiState.update {
                    it.copy(isLoading = false, pose = pose)
                }
            }.onFailure { throwable ->
                Log.e(TAG, "fetchSessionDetail: Failure for id=$id", throwable)
                _uiState.update {
                    it.copy(isLoading = false, error = throwable.message)
                }
            }
        }
    }
}