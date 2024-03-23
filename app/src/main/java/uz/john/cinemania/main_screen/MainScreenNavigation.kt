package uz.john.cinemania.main_screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uz.john.cinemania.R
import uz.john.for_you.presentation.for_you_screen.FOR_YOU_ROUTE
import uz.john.home.presentation.home_screen.HOME_ROUTE
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenParam
import uz.john.paginated_movies_list.all_people_screen.AllPeopleScreenParam
import uz.john.paginated_movies_list.all_tv_shows_screen.AllTvShowsScreenParam
import uz.john.profile.presentation.profile_screen.PROFILE_SCREEN
import uz.john.search.presentation.search_screen.SEARCH_SCREEN
import uz.john.ui.theme.CineManiaIcons

const val MAIN_ROUTE = "MAIN_ROUTE"

fun NavController.navigateToMainScreen(navOptions: NavOptions? = null) {
    navigate(MAIN_ROUTE, navOptions)
}

fun NavGraphBuilder.mainScreen(
    onMovieItemClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    onSeeAllMoviesClick: (AllMoviesScreenParam) -> Unit,
    onSeeAllPeopleClick: (AllPeopleScreenParam) -> Unit,
    onSeeAllTvShowsClick: (AllTvShowsScreenParam) -> Unit
) {
    composable(MAIN_ROUTE) {
        val bottomNavController = rememberNavController()
        MainScreen(
            bottomNavController = bottomNavController,
            onMovieItemClick = onMovieItemClick,
            onSeeAllMoviesClick = onSeeAllMoviesClick,
            onPersonClick = onPersonClick,
            onTvShowClick = onTvShowClick,
            onSeeAllTvShowsClick = onSeeAllTvShowsClick,
            onSeeAllPeopleClick = onSeeAllPeopleClick
        )
    }
}

val bottomNavDestinationRoutes = BottomNavigationItems.entries.map { it.route }

@Immutable
enum class BottomNavigationItems(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int
) {
    HOME_ITEM(
        route = HOME_ROUTE,
        title = R.string.home,
        icon = CineManiaIcons.Home,
        selectedIcon = CineManiaIcons.Filled.Home
    ),

    FOR_YOU_ITEM(
        route = FOR_YOU_ROUTE,
        title = R.string.for_you,
        icon = CineManiaIcons.ForYou,
        selectedIcon = CineManiaIcons.Filled.ForYou
    ),

    SEARCH_ITEM(
        route = SEARCH_SCREEN,
        title = R.string.search,
        icon = CineManiaIcons.Search,
        selectedIcon = CineManiaIcons.Filled.Search
    ),

    PROFILE_ITEM(
        route = PROFILE_SCREEN,
        title = R.string.profile,
        icon = CineManiaIcons.Profile,
        selectedIcon = CineManiaIcons.Filled.Profile
    )
}