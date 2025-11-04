package com.agus.wellnessapp.domain.usecase

import android.util.Log
import com.agus.wellnessapp.data.model.Pose
import com.agus.wellnessapp.data.repository.SessionRepository
import javax.inject.Inject

private const val TAG = "GetSessionsUseCase"

class GetSessionsUseCase @Inject constructor(
    private val repository: SessionRepository
) {
    suspend operator fun invoke(): Result<List<Pose>> {
        Log.d(TAG, "invoke: getting sessions...")
        return repository.getSessions()
    }
}