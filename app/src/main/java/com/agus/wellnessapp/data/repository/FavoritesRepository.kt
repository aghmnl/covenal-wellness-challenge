package com.agus.wellnessapp.data.repository

import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    fun getFavoriteIds(): Flow<Set<String>>

    suspend fun toggleFavorite(id: String)
}