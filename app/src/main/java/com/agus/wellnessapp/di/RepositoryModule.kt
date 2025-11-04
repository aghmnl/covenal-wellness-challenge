package com.agus.wellnessapp.di

import com.agus.wellnessapp.data.repository.FavoritesRepository
import com.agus.wellnessapp.data.repository.FavoritesRepositoryImpl
import com.agus.wellnessapp.data.repository.SessionRepository
import com.agus.wellnessapp.data.repository.SessionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSessionRepository(
        sessionRepositoryImpl: SessionRepositoryImpl
    ): SessionRepository

    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(
        favoritesRepositoryImpl: FavoritesRepositoryImpl
    ): FavoritesRepository
}