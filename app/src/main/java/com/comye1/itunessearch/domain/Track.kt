package com.comye1.itunessearch.domain

/**
 * Track의 domain output model, ui 계층에서 사용됨
 */
data class Track(
    val id: Long,
    val artworkUrl: String,
    val trackName: String,
    val artistName: String,
    val collectionName: String,
    val favorite: Boolean
)
