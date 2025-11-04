package com.agus.wellnessapp.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "FavoritesRepositoryImpl"

@Singleton
class FavoritesRepositoryImpl @Inject constructor() : FavoritesRepository {

    private val _favoriteIds = MutableStateFlow<Set<String>>(emptySet())

    override fun getFavoriteIds(): Flow<Set<String>> {
        Log.d(TAG, "getFavoriteIds: Providing flow of favorites.")
        return _favoriteIds.asStateFlow()
    }

    override suspend fun toggleFavorite(id: String) {
        withContext(Dispatchers.IO) {
            _favoriteIds.update { currentFavorites ->
                val newFavorites = currentFavorites.toMutableSet()
                if (newFavorites.contains(id)) {
                    Log.d(TAG, "toggleFavorite: Removing $id from favorites.")
                    newFavorites.remove(id)
                } else {
                    Log.d(TAG, "toggleFavorite: Adding $id to favorites.")
                    newFavorites.add(id)
                }
                newFavorites
            }
            Log.d(TAG, "toggleFavorite: New favorites list: ${_favoriteIds.value}")
        }
    }
}