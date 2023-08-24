package com.comye1.itunessearch.data.remote

/**
 * Search 요청 응답 모델
 */
data class SearchResponse(
    val resultCount: Int,
    val results: List<TrackResult>
)