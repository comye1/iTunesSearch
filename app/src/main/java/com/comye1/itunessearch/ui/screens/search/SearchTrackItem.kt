package com.comye1.itunessearch.ui.screens.search

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.comye1.itunessearch.R
import com.comye1.itunessearch.domain.Track
import com.comye1.itunessearch.ui.components.TrackItem

@Composable
fun SearchTrackItem(
    modifier: Modifier = Modifier,
    track: Track,
    addToFavorites: (Track) -> Unit,
    removeFromFavorites: (Long) -> Unit
) {
    TrackItem(
        modifier = modifier,
        artworkUrl = track.artworkUrl,
        trackName = track.trackName,
        collectionName = track.collectionName,
        artistName = track.artistName
    ) {
        if (track.favorite) {
            IconButton(onClick = { removeFromFavorites(track.id) }) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = stringResource(id = R.string.remove_from_favorites)
                )
            }
        }else {
            IconButton(onClick = { addToFavorites(track) }) {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = stringResource(id = R.string.add_to_favorites)
                )
            }
        }
    }
}