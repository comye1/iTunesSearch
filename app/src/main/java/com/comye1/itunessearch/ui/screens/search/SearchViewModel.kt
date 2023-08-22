package com.comye1.itunessearch.ui.screens.search

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comye1.itunessearch.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    fun search() {
        pagingManager = SearchPagingManager(
            searchRepository,
            favoritesRepository,
            term = "greenday",
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
        search()
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }
}
