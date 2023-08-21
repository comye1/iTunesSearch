package com.comye1.itunessearch.ui.navigation

sealed class Destination(val route: String) {
    object Search: Destination(route = "search")
    object Favorites: Destination(route = "favorites")
}