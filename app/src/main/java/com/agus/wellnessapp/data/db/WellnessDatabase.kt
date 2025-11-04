package com.agus.wellnessapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoritePoseEntity::class], version = 1, exportSchema = false)
abstract class WellnessDatabase : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao

    companion object {
        const val DATABASE_NAME = "wellness_db"
    }
}