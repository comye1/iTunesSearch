package com.comye1.itunessearch.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.TestOnly

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    fun getAllTracks(): Flow<List<FavoriteTrack>>

    @Query("SELECT * FROM favorites WHERE id = :id")
    suspend fun findTrack(id: Long): FavoriteTrack?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: FavoriteTrack)

    @Delete
    suspend fun delete(track: FavoriteTrack)
}