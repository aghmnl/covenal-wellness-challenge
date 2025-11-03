package com.agus.wellnessapp.data.model

import com.squareup.moshi.Json

data class Pose(
    @field:Json(name = "id")
    val id: Int,

    @field:Json(name = "english_name")
    val englishName: String,

    @field:Json(name = "sanskrit_name_adapted")
    val sanskritName: String,

    @field:Json(name = "pose_description")
    val description: String,

    @field:Json(name = "pose_benefits")
    val benefits: String,

    @field:Json(name = "url_png")
    val imageUrl: String
)