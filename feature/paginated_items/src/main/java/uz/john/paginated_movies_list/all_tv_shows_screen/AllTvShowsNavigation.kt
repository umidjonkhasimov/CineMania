package uz.john.paginated_movies_list.all_tv_shows_screen

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val ALL_TV_SHOWS_ARG = "ALL_TV_SHOWS_ARG"
const val ALL_TV_SHOWS = "ALL_TV_SHOWS"
const val ALL_TV_SHOWS_ROUTE = "$ALL_TV_SHOWS/{$ALL_TV_SHOWS_ARG}"

fun NavController.navigateToAllTvShowsScreen(
    allTvShowsScreenParam: AllTvShowsScreenParam,
    navOptions: NavOptions? = null
) {
    val json = Json.encodeToString(allTvShowsScreenParam)
    navigate(
        route = "$ALL_TV_SHOWS/$json",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.allTvShowsScreen(
    onBackClick: () -> Unit,
    onTvShowClick: (Int) -> Unit
) {
    composable(route = ALL_TV_SHOWS_ROUTE) {
        AllTvShowsScreen(
            onBackClick = onBackClick,
            onTvShowClick = onTvShowClick
        )
    }
}