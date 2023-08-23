package com.comye1.itunessearch.ui.screens.search

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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
                    // 마지막 페이지가 아닌데 LazyColumn의 마지막 아이템에 다다른 경우 다음 페이지를 요청한다.
                    SideEffect {
                        Log.d("Search", "loading next page")
                        viewModel.loadNextPage()
                    }
                }
            }
        }
    }
}