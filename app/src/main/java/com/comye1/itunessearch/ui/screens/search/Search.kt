package com.comye1.itunessearch.ui.screens.search

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.comye1.itunessearch.R
import com.comye1.itunessearch.ui.components.Notice

@Composable
fun Search(viewModel: SearchViewModel) {
    val tracks by viewModel.pagingFlow.collectAsState()
    val state by viewModel.searchState.collectAsState()

    val lazyState = rememberLazyListState()
    val focusManager = LocalFocusManager.current // 검색 후 포커스 관리

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier.padding(16.dp),
            searchWord = state.term,
            setSearchWord = viewModel::setTerm
        ) {
            focusManager.clearFocus()
            viewModel.search()
        }

        LazyColumn(
            state = lazyState,
            contentPadding = PaddingValues(horizontal = 16.dp),
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
            if (viewModel.pagingState.error) {
                item {
                    Column(Modifier.fillMaxWidth(), horizontalAlignment = CenterHorizontally) {
                        Notice(
                            modifier = Modifier.fillMaxWidth(),
                            message = stringResource(id = R.string.error_message)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadPage() }) {
                            Text(text = stringResource(id = R.string.retry))
                        }
                    }
                }
            }
            else if (!viewModel.pagingState.loading && tracks.isEmpty()) {
                item {
                    Notice(
                        modifier = Modifier.fillMaxWidth(),
                        message = stringResource(id = R.string.search_no_result)
                    )
                }
            }
            else {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (viewModel.pagingState.loading) {
                            CircularProgressIndicator()
                        } else if (!viewModel.pagingState.isLastPage) {
                            // 마지막 페이지가 아닌데 LazyColumn의 마지막 아이템에 다다른 경우 다음 페이지를 요청한다.
                            SideEffect {
                                Log.d("Search", "loading next page")
                                viewModel.loadPage()
                            }
                        }
                    }
                }
            }
        }
    }
}