package com.agus.wellnessapp.domain.usecase

import android.util.Log
import com.agus.wellnessapp.data.repository.FavoritesRepository
import javax.inject.Inject

private const val TAG = "ToggleFavoriteUseCase"

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    suspend operator fun invoke(id: String) {
        Log.d(TAG, "invoke: toggling favorite for id=$id")
        repository.toggleFavorite(id)
    }
}