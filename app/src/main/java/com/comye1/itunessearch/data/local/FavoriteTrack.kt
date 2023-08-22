package com.comye1.itunessearch.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteTrack(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "artwork_url") val artworkUrl: String,
    @ColumnInfo(name = "track_name") val trackName: String,
    @ColumnInfo(name = "artist_name") val artistName: String,
    @ColumnInfo(name = "collection_name") val collectionName: String,
)