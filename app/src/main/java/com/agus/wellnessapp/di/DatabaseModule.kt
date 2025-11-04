package com.agus.wellnessapp.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.agus.wellnessapp.data.db.FavoritesDao
import com.agus.wellnessapp.data.db.WellnessDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val TAG = "DatabaseModule"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WellnessDatabase {
        Log.d(TAG, "Providing Room database...")
        return Room.databaseBuilder(
            context,
            WellnessDatabase::class.java,
            WellnessDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoritesDao(database: WellnessDatabase): FavoritesDao {
        Log.d(TAG, "Providing FavoritesDao...")
        return database.favoritesDao()
    }
}