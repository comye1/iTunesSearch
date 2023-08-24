package com.comye1.itunessearch.di

import android.content.Context
import androidx.room.Room
import com.comye1.itunessearch.data.local.FavoritesDao
import com.comye1.itunessearch.data.local.FavoritesDatabase
import com.comye1.itunessearch.data.remote.SearchApi
import com.comye1.itunessearch.data.repositories.FavoritesRepositoryImpl
import com.comye1.itunessearch.data.repositories.SearchRepositoryImpl
import com.comye1.itunessearch.domain.FavoritesRepository
import com.comye1.itunessearch.domain.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://itunes.apple.com"
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFavoriteDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            FavoritesDatabase::class.java,
            "favorites"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideFavoritesDao(database: FavoritesDatabase) = database.favoritesDao()

    @Provides
    @Singleton
    fun provideSearchRepository(searchApi: SearchApi): SearchRepository =
        SearchRepositoryImpl(searchApi = searchApi)

    @Provides
    @Singleton
    fun provideFavoriteRepository(dao: FavoritesDao): FavoritesRepository =
        FavoritesRepositoryImpl(dao = dao)

}