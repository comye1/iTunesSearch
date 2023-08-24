package com.comye1.itunessearch.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@Composable
fun Search(viewModel: SearchViewModel) {
    val tracks by viewModel.pagingFlow.collectAsState()
    val state by viewModel.searchState.collectAsState()

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

        SearchResult(
            modifier = Modifier.fillMaxSize(),
            tracks = tracks,
            pagingState = viewModel.pagingState,
            addToFavorites = viewModel::addToFavorites,
            removeFavoriteTrack = viewModel::removeFavoriteTrack,
            loadPage = viewModel::loadPage
        )
    }
}