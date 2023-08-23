package com.comye1.itunessearch.domain

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchPagingManager(
    private val searchRepository: SearchRepository,
    private val favoriteRepository: FavoritesRepository,
    private val scope: CoroutineScope
) {

    private val _flow = MutableStateFlow<List<Track>>(listOf())
    val flow: StateFlow<List<Track>> = _flow

    private var _state by mutableStateOf(PagingState(term = ""))
    val state
        get() = _state

    private val favorites = hashSetOf<Long>()

    /**
     * 새로운 검색 결과로 초기화한다.
     */
    fun initialize(term: String) {
        term.trim().let {
            // 공백, 동일 검색어 체크
            if (it.isNotEmpty() && it != state.term) {
                _state = PagingState(term = term)
                _flow.update { listOf() }
                loadPage()
            }
        }
    }

    /**
     * 이번 페이지를 불러온다. (에러인 경우 페이지가 유지되므로 loadPage로 재시도)
     */
    fun loadPage() {
        scope.launch {
            _state = state.copy(loading = true, error = false)
            Log.d(TAG, "term : ${state.term}")
            Log.d(TAG, "current page : ${state.currentPage}")
            searchRepository.getSearchResult(
                term = state.term,
                page = state.currentPage,
                size = 30
            ).onSuccess {
                Log.d(TAG, "onSuccess ${it}")
                it.data.map { result ->
                    Track(
                        id = result.trackId,
                        artworkUrl = result.artworkUrl60 ?: "",
                        trackName = result.trackName ?: "(트랙 정보 없음)",
                        artistName = result.artistName ?: "(아티스트 정보 없음)",
                        collectionName = result.collectionName ?: "(컬렉션 정보 없음)",
                        favorite = favorites.contains(result.trackId)
                    )
                }.also { newList ->
                    _flow.update { list ->
                        (list + newList).distinct() // 서버에서 중복된 결과를 보내주는 경우가 있음
                    }
                    _state = state.copy(
                        currentPage = state.currentPage + 1,
                        isLastPage = it.lastPage,
                        loading = false
                    )
                }
            }.onFailure {
                _state = state.copy(loading = false, error = true)
            }
        }
    }

    /**
     * favorites 목록을 collect하여 track 리스트의 favorite 여부를 업데이트한다.
     */
    private fun collectFavorites() {
        scope.launch(Dispatchers.IO) {
            favoriteRepository.getFavoriteTracks().collectLatest { favoriteList ->
                Log.d(TAG, favoriteList.joinToString(" "))
                favorites.clear()
                favoriteList.forEach {
                    favorites.add(it.id)
                }
                filterFavorites()
            }
        }
    }

    /**
     * track 리스트의 favorite 여부를 업데이트한다.
     */
    private fun filterFavorites() {
        _flow.update {
            it.map { track ->
                track.copy(favorite = favorites.contains(track.id))
            }
        }
    }

    init {
        collectFavorites()
    }

    companion object {
        private const val TAG = "SearchPagingSource"
    }
}

@Immutable
data class PagingState(
    val term: String = "",
    val currentPage: Int = 0,
    val isLastPage: Boolean = false,
    val loading: Boolean = false,
    val error: Boolean = false
)