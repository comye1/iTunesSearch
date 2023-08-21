package com.comye1.itunessearch.di

import android.content.Context
import androidx.room.Room
import com.comye1.itunessearch.data.local.FavoritesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideFavoriteDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            FavoritesDatabase::class.java,
            "favorites_db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideFavoritesDao(database: FavoritesDatabase) = database.favoritesDao()
}