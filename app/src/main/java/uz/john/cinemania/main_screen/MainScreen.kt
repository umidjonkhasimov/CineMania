package uz.john.cinemania.main_screen

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import uz.john.for_you.presentation.for_you_screen.forYouScreen
import uz.john.home.presentation.home_screen.HOME_ROUTE
import uz.john.home.presentation.home_screen.homeScreen
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenParam
import uz.john.paginated_movies_list.all_tv_shows_screen.AllTvShowsScreenParam
import uz.john.profile.presentation.profile_screen.profileScreen
import uz.john.search.presentation.search_screen.searchScreen

private val BOTTOM_NAV_BAR_HEIGHT = 50.dp
private val BOTTOM_NAV_BAR_ITEM_HEIGHT = 40.dp

@Composable
fun MainScreen(
    bottomNavController: NavHostController,
    onMovieItemClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    onSeeAllMoviesClick: (AllMoviesScreenParam) -> Unit,
    onSeeAllTvShowsClick: (AllTvShowsScreenParam) -> Unit
) {
    val bottomNavigationItems = listOf(
        BottomNavigationItems.HOME_ITEM,
        BottomNavigationItems.FOR_YOU_ITEM,
        BottomNavigationItems.SEARCH_ITEM,
        BottomNavigationItems.PROFILE_ITEM
    )

    var selectedItem by remember {
        mutableStateOf<String?>(BottomNavigationItems.HOME_ITEM.route)
    }

    bottomNavController.addOnDestinationChangedListener { _, destination, _ ->
        selectedItem = destination.route
    }

    Scaffold(
        bottomBar = {
            CineManiaNavBar(
                modifier = Modifier.height(BOTTOM_NAV_BAR_HEIGHT)
            ) {
                bottomNavigationItems.forEach { item ->
                    CineManiaNavBarItem(
                        selected = selectedItem == item.route,
                        onClick = {
                            if (selectedItem != item.route) {
                                selectedItem = item.route
                                bottomNavController.navigate(
                                    route = item.route,
                                    navOptions = navOptions {
                                        popUpTo(bottomNavController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        restoreState = true
                                        launchSingleTop = true
                                    }
                                )
                            }
                        },
                        label = stringResource(id = item.title),
                        icon = if (selectedItem == item.route) {
                            item.selectedIcon
                        } else {
                            item.icon
                        }

                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            startDestination = HOME_ROUTE,
            navController = bottomNavController,
            enterTransition = {
                if (
                    bottomNavDestinationRoutes.indexOf(initialState.destination.route) <
                    bottomNavDestinationRoutes.indexOf(targetState.destination.route)
                ) {
                    slideInHorizontally { it }
                } else {
                    slideInHorizontally { -it }
                }
            },
            exitTransition = {
                if (
                    bottomNavDestinationRoutes.indexOf(initialState.destination.route) <
                    bottomNavDestinationRoutes.indexOf(targetState.destination.route)
                ) {
                    slideOutHorizontally { -it }
                } else {
                    slideOutHorizontally { it }
                }
            }
        ) {
            homeScreen(
                onMovieItemClick = onMovieItemClick,
                onSeeAllClick = onSeeAllMoviesClick
            )
            forYouScreen()
            profileScreen()
            searchScreen(
                onPersonClick = onPersonClick,
                onMovieClick = onMovieItemClick,
                onTvShowClick = onTvShowClick,
                onSeeAllMoviesClick = onSeeAllMoviesClick,
                onSeeAllPeopleClick = { },
                onSeeAllTvShowsClick = onSeeAllTvShowsClick
            )
        }
    }
}

@Composable
fun CineManiaNavBar(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        color = containerColor,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(windowInsets)
                .padding(horizontal = 16.dp)
                .defaultMinSize(minHeight = 56.dp)
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Composable
fun RowScope.CineManiaNavBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    @DrawableRes
    icon: Int,
    label: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .weight(1f)
            .height(BOTTOM_NAV_BAR_ITEM_HEIGHT)
            .clip(MaterialTheme.shapes.small)
            .background(
                if (selected)
                    MaterialTheme.colorScheme.surfaceVariant
                else
                    Color.Transparent
            )
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = label,
            tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
        )
        AnimatedVisibility(
            visible = selected,
            enter = scaleIn() + fadeIn()
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
            )
        }
    }
}