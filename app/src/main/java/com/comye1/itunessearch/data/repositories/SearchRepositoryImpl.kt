package com.comye1.itunessearch.data.repositories

import com.comye1.itunessearch.data.remote.SearchApi
import com.comye1.itunessearch.domain.SearchRepository
import com.comye1.itunessearch.domain.SearchResult
import javax.inject.Inject


class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
) : SearchRepository {
    override suspend fun getSearchResult(term: String, page: Int, size: Int): Result<SearchResult> {
        val response = try {
            searchApi.searchTrack(term = term, page = page, size = size)
        } catch (e: Exception) {
            return Result.failure(e)
        }
        return Result.success(SearchResult(page, response.resultCount < size, response.results))
    }
}