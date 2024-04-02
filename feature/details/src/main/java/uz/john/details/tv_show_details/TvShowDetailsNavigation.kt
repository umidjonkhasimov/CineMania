package uz.john.details.tv_show_details

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import uz.john.paginated_movies_list.all_tv_shows_screen.AllTvShowsScreenParam

const val TV_SHOW_ID_ARG = "TV_SHOW_ID_ARG"
const val TV_SHOW_DETAILS = "TV_SHOW_DETAILS"
const val TV_SHOW_DETAILS_ROUTE = "$TV_SHOW_DETAILS/{$TV_SHOW_ID_ARG}"

fun NavHostController.navigateToTvShowsDetailsScreen(seriesId: Int, navOptions: NavOptions? = null) {
    navigate(
        route = "$TV_SHOW_DETAILS/$seriesId",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.tvShowDetailsScreen(
    onBackClick: () -> Unit,
    onImageClick: (String) -> Unit,
    onTvShowClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit,
    onSeeAllTvShowsClick: (AllTvShowsScreenParam) -> Unit,
    onSignInClick: () -> Unit
) {
    composable(route = TV_SHOW_DETAILS_ROUTE) {
        TvShowDetailsScreen(
            onBackClick = onBackClick,
            onImageClick = onImageClick,
            onTvShowClick = onTvShowClick,
            onPersonClick = onPersonClick,
            onSeeAllTvShowsClick = onSeeAllTvShowsClick,
            onSignInClick = onSignInClick
        )
    }
}