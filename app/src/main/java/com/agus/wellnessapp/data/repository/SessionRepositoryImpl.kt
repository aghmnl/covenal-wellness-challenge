package com.agus.wellnessapp.data.repository

import com.agus.wellnessapp.data.model.Pose
import com.agus.wellnessapp.data.network.YogaApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of the SessionRepository.
 * Hilt will inject the 'apiService' for us.
 */
class SessionRepositoryImpl @Inject constructor(
    private val apiService: YogaApiService
) : SessionRepository {

    /**
     * Fetches sessions from the API.
     * We use withContext(Dispatchers.IO) to run this on a background thread.
     * We wrap the call in Result.runCatching to gracefully handle network errors.
     */
    override suspend fun getSessions(): Result<List<Pose>> {
        return withContext(Dispatchers.IO) {
            Result.runCatching {
                apiService.getPoses()
            }
        }
    }
}