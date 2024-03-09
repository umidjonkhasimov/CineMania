package uz.john.home.presentation.all_movies_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import uz.john.home.presentation.home_screen.AllMoviesMediaType

const val ALL_MOVIES_ARG = "ALL_MOVIES_ARG"
const val ALL_MOVIES = "ALL_MOVIES"
const val ALL_MOVIES_ROUTE = "$ALL_MOVIES/{$ALL_MOVIES_ARG}"

fun NavController.navigateToAllMoviesScreen(allMoviesMediaType: AllMoviesMediaType, navOptions: NavOptions? = null) {
    /**
     * As All Movies Screen is universal,
     * we pass encoded custom object to decide which endpoint to trigger.
     * We decode custom object in the viewModel
     * */
    val json = Json.encodeToString(allMoviesMediaType)
    navigate(
        route = "$ALL_MOVIES/$json",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.allMoviesScreen(
    onMovieItemClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    composable(ALL_MOVIES_ROUTE) {
        AllMoviesScreen(
            onMovieItemClick = onMovieItemClick,
            onBackClick = onBackClick
        )
    }
}
