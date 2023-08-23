package com.comye1.itunessearch.data.repositories

import android.util.Log
import com.comye1.itunessearch.data.remote.SearchApi
import com.comye1.itunessearch.domain.SearchRepository
import com.comye1.itunessearch.domain.SearchResult
import javax.inject.Inject


class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
) : SearchRepository {
    override suspend fun getSearchResult(term: String, page: Int, size: Int): Result<SearchResult> {
        val response = try {
            searchApi.searchTrack(
                term = term,
                offset = page * size, // page를 offset으로 변환환
                limit = size,
                entity = "song"
            )
        } catch (e: Exception) {
            Log.d("SearchRepository", e.localizedMessage)
            return Result.failure(e)
        }
        return Result.success(
            SearchResult(
                lastPage = response.resultCount < size, // 요청한 사이즈보다 결과 개수가 작다면 마지막 페이지이다.
                data = response.results
            )
        )
    }
}