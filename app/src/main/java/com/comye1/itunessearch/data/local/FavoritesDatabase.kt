package com.comye1.itunessearch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        TrackEntity::class
    ], version = 2
)
abstract class FavoritesDatabase: RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}