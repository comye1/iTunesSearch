package com.comye1.itunessearch.ui.screens.favorites

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comye1.itunessearch.domain.FavoritesRepository
import com.comye1.itunessearch.domain.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _favoritesState = MutableStateFlow(FavoritesState())
    val favoritesState: StateFlow<FavoritesState> = _favoritesState


    private fun collectFavoriteTracks() {
        viewModelScope.launch(Dispatchers.IO) {
            favoritesRepository.getFavoriteTracks().collectLatest { list ->
                _favoritesState.update {
                    FavoritesState(list)
                }
            }
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
        collectFavoriteTracks()
    }
}

@Immutable
data class FavoritesState(
    val trackList: List<Track> = listOf(),
)
