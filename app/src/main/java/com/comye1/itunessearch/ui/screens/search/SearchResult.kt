package com.comye1.itunessearch.ui.screens.search

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.comye1.itunessearch.R
import com.comye1.itunessearch.domain.PagingState
import com.comye1.itunessearch.domain.Track
import com.comye1.itunessearch.ui.components.Notice

@Composable
fun SearchResult(
    modifier: Modifier = Modifier,
    tracks: List<Track>,
    pagingState: PagingState,
    addToFavorites: (Track) -> Unit,
    removeFavoriteTrack: (Track) -> Unit,
    loadPage: () -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(
            items = tracks,
            key = { it.id }
        ) {
            SearchTrackItem(
                track = it,
                addToFavorites = addToFavorites,
                removeFromFavorites = removeFavoriteTrack
            ){
                // onClick
            }
        }
        if (pagingState.error) { // 에러 발생
            item {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Notice(
                        modifier = Modifier.fillMaxWidth(),
                        message = stringResource(id = R.string.error_message)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = loadPage) { // 재시도 버튼 -> 해당 페이지를 다시 요청
                        Text(text = stringResource(id = R.string.retry))
                    }
                }
            }
        } else if (!pagingState.loading && tracks.isEmpty()) { // 검색 결과 없는 경우
            item {
                Notice(
                    modifier = Modifier.fillMaxWidth(),
                    message = stringResource(id = R.string.search_no_result)
                )
            }
        } else {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (pagingState.loading) {
                        CircularProgressIndicator()
                    } else if (!pagingState.isLastPage) {
                        // 마지막 페이지가 아닌데 LazyColumn의 마지막 아이템에 다다른 경우 다음 페이지를 요청한다.
                        SideEffect {
                            Log.d("Search", "loading next page")
                            loadPage()
                        }
                    }
                }
            }
        }
    }
}