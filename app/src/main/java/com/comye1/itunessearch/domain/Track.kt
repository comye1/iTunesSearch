package com.comye1.itunessearch.domain

data class Track(
    val id: Long,
    val artworkUrl: String,
    val trackName: String,
    val artistName: String,
    val collectionName: String,
    val favorite: Boolean
)
