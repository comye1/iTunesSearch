package com.comye1.itunessearch.domain

import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavoriteTracks(): Flow<List<Track>>
    suspend fun addToFavorites(track: Track): Result<Boolean>
    suspend fun removeFromFavorites(trackId: Long): Result<Boolean>
}