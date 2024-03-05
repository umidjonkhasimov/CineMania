package uz.john.search.presentation.search_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val SEARCH_SCREEN = "SEARCH_SCREEN"

fun NavController.navigateToSearchScreen(navOptions: NavOptions? = null) {
    navigate(SEARCH_SCREEN, navOptions)
}

fun NavGraphBuilder.searchScreen() {
    composable(route = SEARCH_SCREEN) {
        SearchScreen()
    }
}