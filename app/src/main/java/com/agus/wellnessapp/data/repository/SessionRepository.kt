package com.agus.wellnessapp.data.repository

import com.agus.wellnessapp.data.model.Pose

interface SessionRepository {
    suspend fun getSessions(): Result<List<Pose>>

    suspend fun getSessionDetail(id: String): Result<Pose>
}