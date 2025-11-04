package com.agus.wellnessapp.data.network

import com.agus.wellnessapp.data.model.Pose
import retrofit2.http.GET
import retrofit2.http.Query

interface YogaApiService {
    @GET("poses")
    suspend fun getPoses(): List<Pose>

    @GET("poses")
    suspend fun getPoseById(@Query("id") id: String): Pose

    companion object {
        const val BASE_URL = "https://yoga-api-nzy4.onrender.com/v1/"
    }
}