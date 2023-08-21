package com.comye1.itunessearch.data.remote

data class SearchResponse(
    val resultCount: Int,
    val results: List<TrackResult>
)