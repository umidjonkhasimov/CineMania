package uz.john.home.presentation.home_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val HOME_ROUTE = "HOME_ROUTE"

fun NavController.navigateToHomeScreen(navOptions: NavOptions? = null) {
    navigate(HOME_ROUTE, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onMovieItemClick: (Int) -> Unit,
    onSeeAllClick: (AllMoviesMediaType) -> Unit
) {
    composable(route = HOME_ROUTE) {
        HomeScreen(
            onMovieItemClick = onMovieItemClick,
            onSeeAllClick = onSeeAllClick
        )
    }
}