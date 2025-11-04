package com.agus.wellnessapp.data.repository

import android.util.Log
import com.agus.wellnessapp.data.model.Pose
import com.agus.wellnessapp.data.network.YogaApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "SessionRepositoryImpl"

class SessionRepositoryImpl @Inject constructor(
    private val apiService: YogaApiService
) : SessionRepository {

    override suspend fun getSessions(): Result<List<Pose>> {
        Log.d(TAG, "getSessions: Fetching sessions from API...")
        return withContext(Dispatchers.IO) {
            Result.runCatching {
                Log.d(TAG, "getSessions: API call in progress...")
                apiService.getPoses()
            }.onSuccess { poses ->
                if (poses.isNotEmpty()) {
                    Log.i(TAG, "getSessions: Success! First pose: ${poses[0]}")
                } else {
                    Log.i(TAG, "getSessions: Success! Received an empty list.")
                }

                Log.i(TAG, "getSessions: Successfully fetched ${poses.size} poses.")
            }.onFailure { e ->
                Log.e(TAG, "getSessions: Failed to fetch sessions", e)
            }
        }
    }

    override suspend fun getSessionDetail(id: String): Result<Pose> {
        Log.d(TAG, "getSessionDetail: Fetching pose with id=$id")
        return withContext(Dispatchers.IO) {
            Result.runCatching {
                Log.d(TAG, "getSessionDetail: API call in progress for id=$id")

                val pose = apiService.getPoseById(id)

                Log.i(TAG, "getSessionDetail: Successfully found pose: $pose")
                pose

            }.onFailure { e ->
                Log.e(TAG, "getSessionDetail: Failed to fetch pose id=$id", e)
            }
        }
    }
}