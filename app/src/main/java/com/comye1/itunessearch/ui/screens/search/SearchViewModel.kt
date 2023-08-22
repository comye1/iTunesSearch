package com.comye1.itunessearch.ui.screens.search

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comye1.itunessearch.domain.FavoritesRepository
import com.comye1.itunessearch.domain.SearchRepository
import com.comye1.itunessearch.domain.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    private val _searchState = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> = _searchState

    fun search() {
        viewModelScope.launch {
            Log.d(TAG, "search start")
            searchRepository.getSearchResult("greenday", 0, 30)
                .onSuccess {
                    Log.d(TAG, "search success")
                    it.data.let { list ->
                        _searchState.update {
                            SearchState(list.map { res ->
                                Track(
                                    id = res.trackId,
                                    artworkUrl = res.artworkUrl60 ?: "",
                                    trackName = res.trackName ?: "",
                                    artistName = res.artistName ?: "",
                                    collectionName = res.collectionName ?: "",
                                    favorite = false
                                )
                            })
                        }
                    }
                    Log.d(TAG, searchState.value.trackList.joinToString(" "))
                }
                .onFailure {
                    Log.d(TAG, "search failure ${it.message}")
                }
        }
    }

    fun addToFavorites(track: Track) {
        viewModelScope.launch(Dispatchers.IO) {
            favoritesRepository.addToFavorites(track)
                .onSuccess {  }
                .onFailure {  }
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

@Immutable
data class SearchState(
    val trackList: List<Track> = listOf(),
)
