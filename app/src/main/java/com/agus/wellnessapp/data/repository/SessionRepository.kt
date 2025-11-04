package com.agus.wellnessapp.data.repository

import com.agus.wellnessapp.data.model.Pose
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    /**
     * A flow that emits user-friendly error messages
     * for non-fatal errors (like network timeouts).
     */
    fun getNetworkErrors(): Flow<String>

    /**
     * Gets the list of wellness sessions (poses).
     * Tries remote first, then falls back to local.
     */
    suspend fun getSessions(): Result<List<Pose>>

    /**
     * Gets the details for a single session (pose).
     * Tries remote first, then falls back to local.
     */
    suspend fun getSessionDetail(id: String): Result<Pose>
}