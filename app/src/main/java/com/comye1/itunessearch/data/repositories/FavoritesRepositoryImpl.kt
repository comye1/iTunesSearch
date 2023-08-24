package com.comye1.itunessearch.data.repositories

import android.util.Log
import com.comye1.itunessearch.data.local.FavoriteTrack
import com.comye1.itunessearch.data.local.FavoritesDao
import com.comye1.itunessearch.domain.FavoritesRepository
import com.comye1.itunessearch.domain.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val dao: FavoritesDao
) : FavoritesRepository {
    override fun getFavoriteTracks(): Flow<List<Track>> {
        return dao.getAllTracks().map { list ->
            list.map {
                Track(
                    id = it.id,
                    artworkUrl = it.artworkUrl,
                    trackName = it.trackName,
                    collectionName = it.collectionName,
                    artistName = it.artistName,
                    favorite = true
                )
            }
        }
    }

    override suspend fun addToFavorites(track: Track): Result<Boolean> {
        try {
            dao.insert(
                FavoriteTrack(
                    id = track.id,
                    artworkUrl = track.artworkUrl,
                    trackName = track.trackName,
                    collectionName = track.collectionName,
                    artistName = track.artistName,
                )
            )
        } catch (e: Exception) {
            Log.d("FavoritesRepository", e.localizedMessage)
            return Result.failure(e)
        }
        return Result.success(true)
    }

    override suspend fun removeFromFavorites(trackId: Long): Result<Boolean> {
        try {
            dao.delete(trackId)
        } catch (e: Exception) {
            Log.d("FavoritesRepository", e.localizedMessage)
            return Result.failure(e)
        }
        return Result.success(true)
    }
}