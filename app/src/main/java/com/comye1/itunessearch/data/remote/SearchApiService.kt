package com.comye1.itunessearch.data.remote

import com.comye1.itunessearch.domain.SearchResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class SearchApiService @Inject constructor(
    private val httpClient: HttpClient
) : SearchApi {
    override suspend fun searchTrack(term: String, page: Int, size: Int): SearchResult {
        val response = try {
            httpClient.get {
                url(ROUTE_SEARCH)
                contentType(ContentType.Application.Json)
                parameter("term", term)
                parameter("offset", page.toString())
                parameter("limit", size.toString())
            }.body<SearchResponse>()
        } catch (e: Exception) {
            throw e
        }
        return SearchResult(
            page = page,
            lastPage = response.resultCount < page,
            data = response.results
        )
    }

    companion object {
        val ROUTE_SEARCH = "https://itunes.apple.com/search"
    }
}