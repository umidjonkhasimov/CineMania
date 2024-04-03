package uz.john.home.presentation.home_screen

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import uz.john.home.R
import uz.john.home.presentation.components.HomeShimmerEffect
import uz.john.home.presentation.components.MoviesTabContent
import uz.john.home.presentation.components.TabIndicator
import uz.john.home.presentation.components.TvShowsTabContent
import uz.john.home.presentation.home_screen.HomeScreenContract.SideEffect
import uz.john.home.presentation.home_screen.HomeScreenContract.UiAction
import uz.john.home.presentation.home_screen.HomeScreenContract.UiState
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenParam
import uz.john.paginated_movies_list.all_tv_shows_screen.AllTvShowsScreenParam
import uz.john.ui.components.CineManiaErrorDialog

@Composable
fun HomeScreen(
    onMovieItemClick: (Int) -> Unit,
    onTvShowItemClick: (Int) -> Unit,
    onSeeAllMoviesClick: (AllMoviesScreenParam) -> Unit,
    onSeeAllTvShowsClick: (AllTvShowsScreenParam) -> Unit
) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    HomeScreenContent(
        uiState = uiState,
        onUiAction = viewModel::onAction,
        sideEffect = viewModel.sideEffect,
        onMovieItemClick = onMovieItemClick,
        onTvShowItemClick = onTvShowItemClick,
        onSeeAllMoviesClick = onSeeAllMoviesClick,
        onSeeAllTvShowsClick = onSeeAllTvShowsClick
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenContent(
    uiState: UiState,
    onUiAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
    onMovieItemClick: (Int) -> Unit,
    onTvShowItemClick: (Int) -> Unit,
    onSeeAllMoviesClick: (AllMoviesScreenParam) -> Unit,
    onSeeAllTvShowsClick: (AllTvShowsScreenParam) -> Unit
) {
    var shouldShowErrorDialog by remember { mutableStateOf(false) }
    var dialogErrorMessage by remember { mutableStateOf("") }

    LaunchedEffect(sideEffect) {
        sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SideEffect.ShowErrorDialog -> {
                    shouldShowErrorDialog = true
                    dialogErrorMessage = sideEffect.errorMessage
                }
            }
        }
    }

    if (shouldShowErrorDialog) {
        CineManiaErrorDialog(
            errorText = dialogErrorMessage,
            onCancel = {
                shouldShowErrorDialog = false
            },
            onRetry = {
                shouldShowErrorDialog = false
                onUiAction(UiAction.InitializeMoviesScreen)
            },
            onDismissRequest = {
                shouldShowErrorDialog = false
            }
        )
    }

    Scaffold { paddingValues ->
        val tabs = listOf(
            HomeTabs.MoviesTab,
            HomeTabs.TvShowsTab
        )
        val tabPagerState = rememberPagerState { tabs.size }
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            TabRow(
                modifier = Modifier
                    .fillMaxWidth(.6f),
                selectedTabIndex = tabPagerState.currentPage,
                indicator = {
                    TabIndicator(
                        tabPositions = it,
                        pagerState = tabPagerState
                    )
                },
                divider = {}
            ) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        modifier = Modifier.clip(MaterialTheme.shapes.small),
                        selected = tabPagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                tabPagerState.animateScrollToPage(index)
                            }
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = stringResource(tab.nameRes),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            if (uiState.isLoading)
                HomeShimmerEffect(modifier = Modifier.padding(paddingValues))
            else {
                HorizontalPager(
                    state = tabPagerState
                ) { page ->
                    when (tabs[page]) {
                        is HomeTabs.MoviesTab -> {
                            MoviesTabContent(
                                uiState = uiState,
                                onMovieItemClick = onMovieItemClick,
                                onSeeAllClick = onSeeAllMoviesClick
                            )
                        }

                        is HomeTabs.TvShowsTab -> {
                            TvShowsTabContent(
                                uiState = uiState,
                                onTvShowClick = onTvShowItemClick,
                                onSeeAllClick = onSeeAllTvShowsClick
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed class HomeTabs(
    @StringRes
    val nameRes: Int
) {
    data object MoviesTab : HomeTabs(nameRes = R.string.movies)
    data object TvShowsTab : HomeTabs(nameRes = R.string.tv_shows)
}