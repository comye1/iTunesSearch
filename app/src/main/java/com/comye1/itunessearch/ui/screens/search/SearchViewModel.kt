package com.comye1.itunessearch.ui.screens.search

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comye1.itunessearch.domain.FavoritesRepository
import com.comye1.itunessearch.domain.SearchPagingManager
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
    searchRepository: SearchRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _searchState = MutableStateFlow(SearchState(term = "greenday"))
    val searchState: StateFlow<SearchState> = _searchState

    private val pagingManager = SearchPagingManager(
        searchRepository,
        favoritesRepository,
        scope = viewModelScope
    )

    val pagingState
        get() = pagingManager.state

    val pagingFlow
        get() = pagingManager.flow

    fun setTerm(term: String) {
        _searchState.update {
            SearchState(term)
        }
    }

    /**
     * 현재 검색어로 pagingManager를 초기화한다.
     */
    fun search() {
        pagingManager.initialize(searchState.value.term)
    }

    /**
     * 현재 필요한 페이지를 불러온다.
     */
    fun loadPage() {
        Log.d(TAG, "loadPage")
        pagingManager.loadPage()
    }

    /**
     * Favorites에 추가한다.
     */
    fun addToFavorites(track: Track) {
        viewModelScope.launch(Dispatchers.IO) {
            favoritesRepository.addToFavorites(track)
                .onSuccess { }
                .onFailure { }
        }
    }

    /**
     * Favorites에서 삭제한다.
     */
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
    val term: String = ""
)
