package com.comye1.itunessearch.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): Flow<List<FavoriteTrack>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(track: FavoriteTrack)

    @Delete
    fun delete(track: FavoriteTrack)
}