package com.comye1.itunessearch.domain

import com.comye1.itunessearch.data.remote.TrackResult

/**
 * 검색 결과 domain input model
 */
data class SearchResult(
    val lastPage: Boolean,
    val data: List<TrackResult>
)
