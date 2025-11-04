package com.agus.wellnessapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Defines the database table for storing a favorite pose.
 * The table will have one column, "poseId", which is the primary key.
 */
@Entity(tableName = "favorites")
data class FavoritePoseEntity(
    @PrimaryKey
    val poseId: String
)