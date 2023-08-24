package com.comye1.itunessearch.data.remote

/**
 * Search 요청 응답 아이템 모델
 */
data class TrackResult(
    val artistId: Long,
    val artistName: String?,
    val artistViewUrl: String?,
    val artworkUrl100: String?,
    val artworkUrl60: String?,
    val collectionCensoredName: String?,
    val collectionExplicitness: String?,
    val collectionId: Long,
    val collectionName: String?,
    val collectionPrice: Double,
    val collectionViewUrl: String?,
    val country: String?,
    val currency: String?,
    val discCount: Int,
    val discNumber: Int,
    val kind: String?,
    val previewUrl: String?,
    val primaryGenreName: String?,
    val trackCensoredName: String?,
    val trackCount: Int,
    val trackExplicitness: String?,
    val trackId: Long,
    val trackName: String?,
    val trackNumber: Int,
    val trackPrice: Double,
    val trackTimeMillis: Int,
    val trackViewUrl: String?,
    val wrapperType: String?
)