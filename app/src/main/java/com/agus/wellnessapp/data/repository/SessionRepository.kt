package com.agus.wellnessapp.data.repository

import com.agus.wellnessapp.data.model.Pose

/**
 * Interface for the data layer. This abstracts the data source
 * (network or database) from the ViewModels.
 */
interface SessionRepository {

    /**
     * Gets the list of wellness sessions (poses).
     * We return a Result to handle success or failure states.
     */
    suspend fun getSessions(): Result<List<Pose>>
}