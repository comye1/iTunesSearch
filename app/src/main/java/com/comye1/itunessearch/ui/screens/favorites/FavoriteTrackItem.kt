package com.comye1.itunessearch.ui.screens.favorites

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.comye1.itunessearch.R
import com.comye1.itunessearch.domain.Track
import com.comye1.itunessearch.ui.components.TrackItem

@Composable
fun FavoriteTrackItem(modifier: Modifier = Modifier, track: Track, removeFromFavorites: (Long) -> Unit) {
    TrackItem(
        modifier = modifier,
        artworkUrl = track.artworkUrl,
        trackName = track.trackName,
        collectionName = track.collectionName,
        artistName = track.artistName
    ) {
        IconButton(onClick = { removeFromFavorites(track.id) }) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = stringResource(id = R.string.remove_from_favorites)
            )
        }
    }
}