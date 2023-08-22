package com.comye1.itunessearch.domain

interface SearchRepository {
    suspend fun getSearchResult(term: String, page: Int, size: Int): Result<SearchResult>
}