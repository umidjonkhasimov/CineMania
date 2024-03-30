package uz.john.details.movie_details_screen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenParam

const val MOVIE_ID_ARG = "MOVIE_ID_ARG"
const val MOVIE_DETAILS = "MOVIE_DETAILS"
const val MOVIE_DETAILS_ROUTE = "$MOVIE_DETAILS/{$MOVIE_ID_ARG}"

fun NavHostController.navigateToMovieDetailsScreen(movieId: Int, navOptions: NavOptions? = null) {
    navigate(
        route = "$MOVIE_DETAILS/$movieId",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.movieDetailsScreen(
    onBackClick: () -> Unit,
    onImageClick: (String) -> Unit,
    onMovieClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit,
    onSeeAllSimilarClick: (AllMoviesScreenParam) -> Unit,
    onSignInClick: () -> Unit
) {
    composable(MOVIE_DETAILS_ROUTE) {
        MovieDetailsScreen(
            onBackClick = onBackClick,
            onImageClick = onImageClick,
            onMovieClick = onMovieClick,
            onPersonClick = onPersonClick,
            onSeeAllSimilarClick = onSeeAllSimilarClick,
            onSignInClick = onSignInClick
        )
    }
}