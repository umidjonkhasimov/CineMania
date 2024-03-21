package uz.john.paginated_movies_list.all_movies_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val ALL_MOVIES_ARG = "ALL_MOVIES_ARG"
const val ALL_MOVIES = "ALL_MOVIES"
const val ALL_MOVIES_ROUTE = "$ALL_MOVIES/{$ALL_MOVIES_ARG}"

fun NavController.navigateToAllMoviesScreen(allMoviesScreenParam: AllMoviesScreenParam, navOptions: NavOptions? = null) {
    /**
     * As All Movies Screen is universal,
     * we pass encoded custom object to decide which endpoint to trigger.
     * We decode custom object in the viewModel
     * */
    val json = Json.encodeToString(allMoviesScreenParam)
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
