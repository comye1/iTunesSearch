package com.comye1.itunessearch.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites ORDER BY key DESC")
    fun getAllTracks(): Flow<List<TrackEntity>>

    @Query("SELECT * FROM favorites WHERE track_id = :id")
    suspend fun findTrack(id: Long): TrackEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: TrackEntity)

    @Query("DELETE FROM favorites WHERE track_id = :id")
    suspend fun delete(id: Long)
}