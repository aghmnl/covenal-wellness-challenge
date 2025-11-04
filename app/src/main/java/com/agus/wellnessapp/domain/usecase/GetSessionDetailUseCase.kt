package com.agus.wellnessapp.domain.usecase

import android.util.Log
import com.agus.wellnessapp.data.model.Pose
import com.agus.wellnessapp.data.repository.SessionRepository
import javax.inject.Inject

private const val TAG = "GetSessionDetailUseCase"

class GetSessionDetailUseCase @Inject constructor(
    private val repository: SessionRepository
) {
    suspend operator fun invoke(id: String): Result<Pose> {
        Log.d(TAG, "invoke: getting detail for id=$id")
        return repository.getSessionDetail(id)
    }
}