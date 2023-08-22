package com.comye1.itunessearch.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Search(viewModel: SearchViewModel) {
    val tracks by viewModel.pagingManager.flow.collectAsState()

    val lazyState = rememberLazyListState()

    LazyColumn(
        state = lazyState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(
            items = tracks,
            key = { it.id }
        ) {
            SearchTrackItem(
                track = it,
                addToFavorites = viewModel::addToFavorites,
                removeFromFavorites = viewModel::removeFavoriteTrack
            )
        }
        item {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                if (viewModel.pagingState.loading) {
                    CircularProgressIndicator()
                } else if (!viewModel.pagingState.isLastPage) {
                    Button(onClick = { viewModel.loadNextPage() }) {
                        Text(text = "더 불러오기")
                    }
                }
            }
        }
    }
}