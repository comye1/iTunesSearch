package com.comye1.itunessearch.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.comye1.itunessearch.R

/**
 * Bottom Navigation Bar
 */
@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        BottomNavItem.items.forEach {
            val selected = currentRoute == it.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(it.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = stringResource(id = it.nameRes)
                    )
                },
                label = {
                    Text(text = stringResource(id = it.nameRes))
                },
//                colors = NavigationBarItemDefaults.colors(
//
//                )
            )
        }
    }
}

/**
 * Bottom Navigation Bar를 구성하는 아이템, 리스트
 */
data class BottomNavItem(
    val route: String,
    @StringRes val nameRes: Int,
    val icon: ImageVector,
) {
    companion object {
        val items = listOf(
            BottomNavItem(
                route = Destination.Search.route,
                nameRes = R.string.search_title,
                icon = Icons.Default.Search
            ),
            BottomNavItem(
                route = Destination.Favorites.route,
                nameRes = R.string.favorites_title,
                icon = Icons.Default.Favorite
            ),
        )
    }
}