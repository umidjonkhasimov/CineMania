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
import uz.john.home.presentation.home_screen.AllMoviesMediaType
import uz.john.home.presentation.home_screen.HOME_ROUTE
import uz.john.profile.presentation.profile_screen.PROFILE_SCREEN
import uz.john.search.presentation.search_screen.SEARCH_SCREEN
import uz.john.ui.theme.CineManiaIcons

const val MAIN_ROUTE = "MAIN_ROUTE"

fun NavController.navigateToMainScreen(navOptions: NavOptions? = null) {
    navigate(MAIN_ROUTE, navOptions)
}

fun NavGraphBuilder.mainScreen(
    onMovieItemClick: (Int) -> Unit,
    onSeeAllClick: (AllMoviesMediaType) -> Unit
) {
    composable(MAIN_ROUTE) {
        val bottomNavController = rememberNavController()
        MainScreen(
            bottomNavController = bottomNavController,
            onMovieItemClick = onMovieItemClick,
            onSeeAllClick = onSeeAllClick
        )
    }
}

@Immutable
sealed class BottomNavigationItems(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int
) {
    data object HomeItem :
        BottomNavigationItems(
            route = HOME_ROUTE,
            title = R.string.home,
            icon = CineManiaIcons.Home,
            selectedIcon = CineManiaIcons.Filled.Home
        )

    data object ForYouItem :
        BottomNavigationItems(
            route = FOR_YOU_ROUTE,
            title = R.string.for_you,
            icon = CineManiaIcons.ForYou,
            selectedIcon = CineManiaIcons.Filled.ForYou
        )

    data object SearchItem :
        BottomNavigationItems(
            route = SEARCH_SCREEN,
            title = R.string.search,
            icon = CineManiaIcons.Search,
            selectedIcon = CineManiaIcons.Filled.Search
        )

    data object ProfileItem :
        BottomNavigationItems(
            route = PROFILE_SCREEN,
            title = R.string.profile,
            icon = CineManiaIcons.Profile,
            selectedIcon = CineManiaIcons.Filled.Profile
        )
}