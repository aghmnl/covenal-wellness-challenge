package com.agus.wellnessapp.data.model

import com.squareup.moshi.Json

data class Pose(
    @Json(name = "id")
    val id: Int,

    @Json(name = "english_name")
    val englishName: String? = null,

    @Json(name = "sanskrit_name_adapted")
    val sanskritName: String? = null,

    @Json(name = "pose_description")
    val description: String? = null,

    @Json(name = "pose_benefits")
    val benefits: String? = null,

    @Json(name = "url_png")
    val imageUrl: String? = null
)