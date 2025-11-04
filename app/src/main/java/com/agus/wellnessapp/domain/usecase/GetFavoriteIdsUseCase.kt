package com.agus.wellnessapp.domain.usecase

import android.util.Log
import com.agus.wellnessapp.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "GetFavoriteIdsUseCase"

class GetFavoriteIdsUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {
    operator fun invoke(): Flow<Set<String>> {
        Log.d(TAG, "invoke: getting favorites flow")
        return repository.getFavoriteIds()
    }
}