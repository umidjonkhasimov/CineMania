package uz.john.search.presentation.search_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import uz.john.paginated_movies_list.AllMoviesScreenParam

const val SEARCH_SCREEN = "SEARCH_SCREEN"

fun NavController.navigateToSearchScreen(navOptions: NavOptions? = null) {
    navigate(SEARCH_SCREEN, navOptions)
}

fun NavGraphBuilder.searchScreen(
    onPersonClick: (Int) -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    onSeeAllMoviesClick: (AllMoviesScreenParam) -> Unit,
    onSeeAllPeopleClick: () -> Unit,
    onSeeAllTvShowsClick: () -> Unit
) {
    composable(route = SEARCH_SCREEN) {
        SearchScreen(
            onPersonClick = onPersonClick,
            onMovieClick = onMovieClick,
            onTvShowClick = onTvShowClick,
            onSeeAllPeopleClick = onSeeAllPeopleClick,
            onSeeAllMoviesClick = onSeeAllMoviesClick,
            onSeeAllTvShowsClick = onSeeAllTvShowsClick
        )
    }
}