package com.comye1.itunessearch.di

import com.comye1.itunessearch.data.remote.SearchApi
import com.comye1.itunessearch.data.remote.SearchApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient =
        HttpClient(Android).config {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json()
            }
        }

    @Singleton
    @Provides
    fun provideSearchApiService(httpClient: HttpClient): SearchApi =
        SearchApiService(httpClient = httpClient)
}