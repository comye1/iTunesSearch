package com.comye1.scrapbook.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.comye1.itunessearch.ui.navigation.BottomNavigationBar
import com.comye1.itunessearch.ui.navigation.Destination
import com.comye1.itunessearch.ui.navigation.NavGraph
import com.comye1.itunessearch.ui.theme.ITunesSearchTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ITunesSearchApp() {

    val navController = rememberNavController()

    ITunesSearchTheme() {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    navController = navController
                )
            }
        ) { paddingValues ->
            NavGraph(
                modifier = Modifier.padding(paddingValues),
                startDestination = Destination.Search,
                navController = navController
            )
        }
    }
}