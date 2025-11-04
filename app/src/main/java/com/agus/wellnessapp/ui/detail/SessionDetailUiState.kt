package com.agus.wellnessapp.ui.detail

import com.agus.wellnessapp.data.model.Pose

data class SessionDetailUiState(
    val isLoading: Boolean = false,
    val pose: Pose? = null,
    val error: String? = null
)
