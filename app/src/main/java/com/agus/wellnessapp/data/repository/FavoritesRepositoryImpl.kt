package com.agus.wellnessapp.data.repository

import android.util.Log
import com.agus.wellnessapp.data.db.FavoritesDao
import com.agus.wellnessapp.data.db.FavoritePoseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "FavoritesRepositoryImpl"

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesDao: FavoritesDao
) : FavoritesRepository {

    /**
     * This reads directly from the Room database flow.
     * It maps the List<FavoritePoseEntity> to a Set<String>
     * to match the interface, so the UI code doesn't break.
     */
    override fun getFavoriteIds(): Flow<Set<String>> {
        Log.d(TAG, "getFavoriteIds: Providing Flow from Room.")
        return favoritesDao.getFavoriteEntities().map { entityList ->
            entityList.map { it.poseId }.toSet()
        }
    }

    /** This logic checks the database and inserts or deletes. */
    override suspend fun toggleFavorite(id: String) {
        withContext(Dispatchers.IO) {
            val existing = favoritesDao.getFavoriteById(id)
            if (existing == null) {
                Log.d(TAG, "toggleFavorite: Inserting $id into Room.")
                favoritesDao.insert(FavoritePoseEntity(poseId = id))
            } else {
                Log.d(TAG, "toggleFavorite: Deleting $id from Room.")
                favoritesDao.deleteById(id)
            }
        }
    }
}