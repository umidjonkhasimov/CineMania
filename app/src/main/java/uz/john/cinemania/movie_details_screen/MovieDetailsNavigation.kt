package uz.john.cinemania.movie_details_screen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import uz.john.home.presentation.home_screen.AllMoviesMediaType

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
    onSeeAllSimilarClick: (AllMoviesMediaType) -> Unit
) {
    composable(MOVIE_DETAILS_ROUTE) {
        MovieDetailsScreen(
            onBackClick = onBackClick,
            onImageClick = onImageClick,
            onMovieClick = onMovieClick,
            onSeeAllSimilarClick = onSeeAllSimilarClick
        )
    }
}