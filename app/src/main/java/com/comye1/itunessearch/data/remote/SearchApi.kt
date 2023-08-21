package com.comye1.itunessearch.data.remote

import com.comye1.itunessearch.domain.SearchResult

interface SearchApi {
    suspend fun searchTrack(term: String, page: Int, size: Int): SearchResult
}