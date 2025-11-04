package com.agus.wellnessapp.ui.list

import com.agus.wellnessapp.data.model.Pose

data class SessionListUiState(
    val isLoading: Boolean = false,
    val sessions: List<Pose> = emptyList(),
    val error: String? = null
)