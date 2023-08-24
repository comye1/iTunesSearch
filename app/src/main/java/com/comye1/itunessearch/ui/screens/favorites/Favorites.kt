package com.comye1.itunessearch.ui.screens.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun Favorites(viewModel: FavoritesViewModel) {
    val state by viewModel.favoritesState.collectAsState()

    FavoritesResult(
        state = state,
        removeFavoriteTrack = viewModel::removeFavoriteTrack
    )
}