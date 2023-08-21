package com.comye1.itunessearch.domain

import com.comye1.itunessearch.data.remote.TrackResult

data class SearchResult(
    val page: Int,
    val lastPage: Boolean,
    val data: List<TrackResult>
)
