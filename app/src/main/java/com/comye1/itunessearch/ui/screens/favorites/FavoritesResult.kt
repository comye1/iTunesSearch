package com.comye1.itunessearch.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.comye1.itunessearch.R
import com.comye1.itunessearch.domain.Track
import com.comye1.itunessearch.ui.components.Notice

@Composable
fun FavoritesResult(state: FavoritesState, removeFavoriteTrack: (Track) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = state.trackList) {
            FavoriteTrackItem(
                track = it,
                removeFromFavorites = removeFavoriteTrack
            ){
                // onClick
            }
        }
    }
    if (state.trackList.isEmpty()){
        Notice(
            modifier = Modifier.fillMaxSize(),
            message = stringResource(id = R.string.favorites_no_result)
        )
    }
}