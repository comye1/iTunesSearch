package com.comye1.itunessearch.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comye1.itunessearch.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackItem(
    modifier: Modifier = Modifier,
    artworkUrl: String,
    trackName: String,
    collectionName: String,
    artistName: String,
    onClick: () -> Unit,
    iconButton: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(IntrinsicSize.Min)
                    .padding(top = 16.dp, bottom = 16.dp, start = 16.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                SquareImage(
                    imgUrl = artworkUrl,
                    description = trackName,
                    modifier = Modifier.fillMaxHeight()
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = trackName,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = collectionName,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = artistName,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Box(modifier = Modifier.padding(4.dp)) {
                iconButton()
            }
        }
    }
}

@Preview
@Composable
fun TrackItemPreview() {
    Column(Modifier.fillMaxSize()) {
        TrackItem(
            artworkUrl = "https://i.pinimg.com/originals/a4/b6/fc/a4b6fc000e66d3e07ebea1d9a9bffa33.jpg",
            trackName = "Upside Down aaaaaaaa ekekekek",
            collectionName = "Sing-a-Longs and Lullabies for the Film Curious George",
            artistName = "Jack Johnson",
            onClick = {}
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(id = R.string.remove_from_favorites)
                )
            }
        }
    }
}
