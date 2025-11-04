package com.agus.wellnessapp.di

import android.content.Context
import android.util.Log
import com.agus.wellnessapp.data.network.YogaApiService
import com.agus.wellnessapp.data.repository.SessionRepository
import com.agus.wellnessapp.data.repository.SessionRepositoryImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val TAG = "RepositoryModule"


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSessionRepository(
        apiService: YogaApiService,
        @ApplicationContext context: Context,
        moshi: Moshi
    ): SessionRepository {
        Log.d(TAG, "Providing SessionRepositoryImpl")
        return SessionRepositoryImpl(apiService, context, moshi)
    }
}