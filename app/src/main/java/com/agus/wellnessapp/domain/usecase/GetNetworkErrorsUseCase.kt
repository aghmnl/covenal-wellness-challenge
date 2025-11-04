package com.agus.wellnessapp.domain.usecase

import android.util.Log
import com.agus.wellnessapp.data.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "GetNetworkErrorsUseCase"

class GetNetworkErrorsUseCase @Inject constructor(
    private val repository: SessionRepository
) {
    operator fun invoke(): Flow<String> {
        Log.d(TAG, "invoke: getting network errors flow")
        return repository.getNetworkErrors()
    }
}