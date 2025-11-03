package com.agus.wellnessapp.data.network

import com.agus.wellnessapp.data.model.Pose
import retrofit2.http.GET

interface YogaApiService {
    @GET("poses")
    suspend fun getPoses(): List<Pose>

    companion object {
        const val BASE_URL = "https://yoga-api-nzy4.onrender.com/v1/"
    }
}