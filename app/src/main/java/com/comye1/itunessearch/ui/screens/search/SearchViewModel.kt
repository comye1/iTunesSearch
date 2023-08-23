package com.comye1.itunessearch.ui.screens.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comye1.itunessearch.domain.FavoritesRepository
import com.comye1.itunessearch.domain.SearchPagingManager
import com.comye1.itunessearch.domain.SearchRepository
import com.comye1.itunessearch.domain.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    lateinit var pagingManager: SearchPagingManager

    val pagingState
        get() = pagingManager.state

    fun search(term: String) {
        pagingManager = SearchPagingManager(
            searchRepository,
            favoritesRepository,
            term = term,
            scope = viewModelScope
        )

        pagingManager.loadNextPage()
    }

    fun loadNextPage() {
        Log.d(TAG, "loadNextPage")
        pagingManager.loadNextPage()
    }

    fun addToFavorites(track: Track) {
        viewModelScope.launch(Dispatchers.IO) {
            favoritesRepository.addToFavorites(track)
                .onSuccess { }
                .onFailure { }
        }
    }

    fun removeFavoriteTrack(track: Track) {
        viewModelScope.launch(Dispatchers.IO) {
            favoritesRepository.removeFromFavorites(track.id)
                .onSuccess { }
                .onFailure { }
        }
    }

    init {
        Log.d(TAG, "init")
        search("greenday")
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }
}
