package com.comye1.itunessearch.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp

@Composable
fun Search(viewModel: SearchViewModel) {
    val state by viewModel.searchState.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = state.trackList) {
            SearchTrackItem(
                track = it,
                addToFavorites = viewModel::addToFavorites,
                removeFromFavorites = viewModel::removeFavoriteTrack
            )
        }
    }
}