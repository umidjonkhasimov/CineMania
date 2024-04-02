package uz.john.for_you.presentation.for_you_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenParam
import uz.john.paginated_movies_list.all_tv_shows_screen.AllTvShowsScreenParam

const val FOR_YOU_ROUTE = "FOR_YOU_ROUTE"

fun NavController.navigateToForYouScreen(navOptions: NavOptions? = null) {
    navigate(FOR_YOU_ROUTE, navOptions)
}

fun NavGraphBuilder.forYouScreen(
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    onSignInClick: () -> Unit,
    onSeeAllMoviesClick: (AllMoviesScreenParam) -> Unit,
    onSeeAllTvShowsClick: (AllTvShowsScreenParam) -> Unit
) {
    composable(route = FOR_YOU_ROUTE) {
        ForYouScreen(
            onMovieClick = onMovieClick,
            onTvShowClick = onTvShowClick,
            onSignInClick = onSignInClick,
            onSeeAllMoviesClick = onSeeAllMoviesClick,
            onSeeAllTvShowsClick = onSeeAllTvShowsClick
        )
    }
}