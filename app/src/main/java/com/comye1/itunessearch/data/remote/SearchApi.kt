package com.comye1.itunessearch.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search")
    suspend fun searchTrack(
        @Query("term") term: String,
        @Query("offset") page: Int,
        @Query("limit") size: Int
    ): SearchResponse
}