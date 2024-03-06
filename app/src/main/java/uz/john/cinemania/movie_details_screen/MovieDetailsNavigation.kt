package uz.john.cinemania.movie_details_screen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val MOVIE_ID_ARG = "MOVIE_ID_ARG"
const val ALL_MOVIES = "ALL_MOVIES"
const val ALL_MOVIES_ROUTE = "$ALL_MOVIES/{$MOVIE_ID_ARG}"

fun NavHostController.navigateToMovieDetailsScreen(movieId: Int, navOptions: NavOptions? = null) {
    navigate(
        route = "$ALL_MOVIES/$movieId",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.movieDetailsScreen(
    onBackClick: () -> Unit,
    onImageClick: (String) -> Unit,
    onMovieClick: (Int) -> Unit
) {
    composable(ALL_MOVIES_ROUTE) {
        MovieDetailsScreen(
            onBackClick = onBackClick,
            onImageClick = onImageClick,
            onMovieClick = onMovieClick
        )
    }
}