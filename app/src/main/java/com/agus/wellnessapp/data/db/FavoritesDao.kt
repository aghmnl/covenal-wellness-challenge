package com.agus.wellnessapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    /** Emits the full list of favorite entities whenever the table changes. */
    @Query("SELECT * FROM favorites")
    fun getFavoriteEntities(): Flow<List<FavoritePoseEntity>>

    /** Inserts a new favorite. If it already exists, it does nothing. */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: FavoritePoseEntity)

    /** Checks if a specific pose ID exists in the table. */
    @Query("SELECT * FROM favorites WHERE poseId = :id")
    suspend fun getFavoriteById(id: String): FavoritePoseEntity?

    /** Deletes a favorite by its ID.  */
    @Query("DELETE FROM favorites WHERE poseId = :id")
    suspend fun deleteById(id: String)
}