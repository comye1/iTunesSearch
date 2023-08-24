package com.comye1.itunessearch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.comye1.itunessearch.ui.screens.favorites.Favorites
import com.comye1.itunessearch.ui.screens.favorites.FavoritesViewModel
import com.comye1.itunessearch.ui.screens.search.Search
import com.comye1.itunessearch.ui.screens.search.SearchViewModel

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    startDestination: Destination,
    navController: NavHostController
) {
    val searchViewModel: SearchViewModel = hiltViewModel()
    val favoriteViewModel: FavoritesViewModel = hiltViewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route
    ) {
        composable(route = Destination.Search.route) {
            Search(searchViewModel)
        }

        composable(route = Destination.Favorites.route) {
            Favorites(favoriteViewModel)
        }
    }
}