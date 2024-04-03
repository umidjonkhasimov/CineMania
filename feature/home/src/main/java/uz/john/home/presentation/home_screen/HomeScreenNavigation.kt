package uz.john.home.presentation.home_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenParam
import uz.john.paginated_movies_list.all_tv_shows_screen.AllTvShowsScreenParam

const val HOME_ROUTE = "HOME_ROUTE"

fun NavController.navigateToHomeScreen(navOptions: NavOptions? = null) {
    navigate(HOME_ROUTE, navOptions)
}

fun NavGraphBuilder.homeScreen(
    onMovieItemClick: (Int) -> Unit,
    onTvShowItemClick: (Int) -> Unit,
    onSeeAllMoviesClick: (AllMoviesScreenParam) -> Unit,
    onSeeAllTvShowsClick: (AllTvShowsScreenParam) -> Unit
) {
    composable(route = HOME_ROUTE) {
        HomeScreen(
            onMovieItemClick = onMovieItemClick,
            onTvShowItemClick = onTvShowItemClick,
            onSeeAllMoviesClick = onSeeAllMoviesClick,
            onSeeAllTvShowsClick = onSeeAllTvShowsClick
        )
    }
}