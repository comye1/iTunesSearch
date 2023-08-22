package com.comye1.itunessearch.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites ORDER BY key DESC")
    fun getAllTracks(): Flow<List<FavoriteTrack>>

    @Query("SELECT * FROM favorites WHERE track_id = :id")
    suspend fun findTrack(id: Long): FavoriteTrack?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: FavoriteTrack)

    @Query("DELETE FROM favorites WHERE track_id = :id")
    suspend fun delete(id: Long)
}