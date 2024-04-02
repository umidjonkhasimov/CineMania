package uz.john.for_you.presentation.for_you_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import uz.john.domain.model.movie.Movie
import uz.john.domain.model.tv_show.TvShow
import uz.john.for_you.R
import uz.john.for_you.presentation.for_you_screen.ForYouScreenContract.SideEffect
import uz.john.for_you.presentation.for_you_screen.ForYouScreenContract.UiAction
import uz.john.for_you.presentation.for_you_screen.ForYouScreenContract.UiState
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenParam
import uz.john.paginated_movies_list.all_tv_shows_screen.AllTvShowsScreenParam
import uz.john.ui.components.CineManiaErrorDialog
import uz.john.ui.components.LazyRowItemsHolder
import uz.john.ui.components.MovieCardItem
import uz.john.ui.components.SeeAllItem
import uz.john.ui.components.TvShowItem
import uz.john.ui.theme.CineManiaIcons

private val SCREEN_PADDING = 16.dp

@Composable
fun ForYouScreen(
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    onSignInClick: () -> Unit,
    onSeeAllMoviesClick: (AllMoviesScreenParam) -> Unit,
    onSeeAllTvShowsClick: (AllTvShowsScreenParam) -> Unit
) {
    val viewModel: ForYouScreenViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onAction(UiAction.GetSavedMovies)
    }

    ForYouScreenScreenContent(
        uiState = uiState,
        sideEffect = viewModel.sideEffect,
        onUiAction = viewModel::onAction,
        onMovieClick = onMovieClick,
        onTvShowClick = onTvShowClick,
        onSignInClick = onSignInClick,
        onSeeAllMoviesClick = onSeeAllMoviesClick,
        onSeeAllTvShowsClick = onSeeAllTvShowsClick
    )
}

@Composable
fun ForYouScreenScreenContent(
    uiState: UiState,
    sideEffect: Flow<SideEffect>,
    onUiAction: (UiAction) -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    onSignInClick: () -> Unit,
    onSeeAllMoviesClick: (AllMoviesScreenParam) -> Unit,
    onSeeAllTvShowsClick: (AllTvShowsScreenParam) -> Unit
) {
    var shouldSowErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(sideEffect) {
        sideEffect.collect {
            when (it) {
                is SideEffect.ShowErrorDialog -> {
                    shouldSowErrorDialog = true
                    errorMessage = it.errorMessage
                }
            }
        }
    }

    if (shouldSowErrorDialog) {
        CineManiaErrorDialog(
            errorText = errorMessage,
            onCancel = { shouldSowErrorDialog = false },
            onRetry = { },
            onDismissRequest = { shouldSowErrorDialog = false }
        )
    }

    Scaffold(
        topBar = {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.for_you),
                style = MaterialTheme.typography.titleLarge
            )
        }
    ) { paddingValues ->
        val emptyScreen = uiState.favoriteMovies.isNullOrEmpty() &&
                uiState.watchLaterMovies.isNullOrEmpty() &&
                uiState.favoriteTvShows.isNullOrEmpty() &&
                uiState.watchLaterTvShows.isNullOrEmpty()

        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            if (!uiState.isLoggedIn) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.sign_in_to_your_account),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Button(
                        onClick = onSignInClick
                    ) {
                        Text(
                            text = stringResource(R.string.sign_in),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            if (emptyScreen) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(100.dp),
                        painter = painterResource(CineManiaIcons.EmptyFolder),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.you_haven_t_marked_anything_as_favorites),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            LazyColumn {
                favoriteMovies(
                    favoriteMovies = uiState.favoriteMovies,
                    onMovieClick = onMovieClick,
                    onSeeAllClick = { onSeeAllMoviesClick(AllMoviesScreenParam.AllFavoriteMovies) }
                )

                space()

                watchLaterMovies(
                    watchLaterMovies = uiState.watchLaterMovies,
                    onMovieClick = onMovieClick,
                    onSeeAllClick = { onSeeAllMoviesClick(AllMoviesScreenParam.AllWatchLaterMovies) }
                )

                space()

                favoriteTvShows(
                    favoriteTvShows = uiState.favoriteTvShows,
                    onTvShowClick = onTvShowClick,
                    onSeeAllClick = { onSeeAllTvShowsClick(AllTvShowsScreenParam.AllFavoriteTvShows) }
                )

                space()

                watchLaterTvShows(
                    watchLaterTvShows = uiState.watchLaterTvShows,
                    onTvShowClick = onTvShowClick,
                    onSeeAllClick = { onSeeAllTvShowsClick(AllTvShowsScreenParam.AllWatchLaterTvShows) }
                )
            }
        }
    }
}

private fun LazyListScope.favoriteMovies(
    favoriteMovies: List<Movie>?,
    onMovieClick: (Int) -> Unit,
    onSeeAllClick: () -> Unit
) {
    if (!favoriteMovies.isNullOrEmpty()) {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.favorite_movies),
                shouldShowSeeAllButton = true,
                onSeeAllClick = onSeeAllClick
            ) {
                LazyRow {
                    items(
                        items = favoriteMovies,
                        key = { it.id }
                    ) { movie ->
                        MovieCardItem(movieData = movie, onMovieClick = onMovieClick)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    if (favoriteMovies.size >= 10) {
                        item {
                            SeeAllItem(onSeeAllClick = onSeeAllClick)
                        }
                    }
                }
            }
        }
    }
}

private fun LazyListScope.watchLaterMovies(
    watchLaterMovies: List<Movie>?,
    onMovieClick: (Int) -> Unit,
    onSeeAllClick: () -> Unit
) {
    if (!watchLaterMovies.isNullOrEmpty()) {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.watch_later_movies),
                shouldShowSeeAllButton = true,
                onSeeAllClick = onSeeAllClick
            ) {
                LazyRow {
                    items(
                        items = watchLaterMovies,
                        key = { it.id }
                    ) { movie ->
                        MovieCardItem(movieData = movie, onMovieClick = onMovieClick)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    if (watchLaterMovies.size >= 10) {
                        item {
                            SeeAllItem(onSeeAllClick = onSeeAllClick)
                        }
                    }
                }
            }
        }
    }
}

private fun LazyListScope.favoriteTvShows(
    favoriteTvShows: List<TvShow>?,
    onTvShowClick: (Int) -> Unit,
    onSeeAllClick: () -> Unit
) {
    if (!favoriteTvShows.isNullOrEmpty()) {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.favorite_tv_shows),
                shouldShowSeeAllButton = true,
                onSeeAllClick = onSeeAllClick
            ) {
                LazyRow {
                    items(
                        items = favoriteTvShows,
                        key = { it.id }
                    ) { tvShow ->
                        TvShowItem(tvShow = tvShow, onTvShowClick = onTvShowClick)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    if (favoriteTvShows.size >= 10) {
                        item {
                            SeeAllItem(onSeeAllClick = onSeeAllClick)
                        }
                    }
                }
            }
        }
    }
}

private fun LazyListScope.watchLaterTvShows(
    watchLaterTvShows: List<TvShow>?,
    onTvShowClick: (Int) -> Unit,
    onSeeAllClick: () -> Unit
) {
    if (!watchLaterTvShows.isNullOrEmpty()) {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.watch_later_tv_shows),
                shouldShowSeeAllButton = true,
                onSeeAllClick = onSeeAllClick
            ) {
                LazyRow {
                    items(
                        items = watchLaterTvShows,
                        key = { it.id }
                    ) { tvShow ->
                        TvShowItem(tvShow = tvShow, onTvShowClick = onTvShowClick)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    if (watchLaterTvShows.size >= 10) {
                        item {
                            SeeAllItem(onSeeAllClick = onSeeAllClick)
                        }
                    }
                }
            }
        }
    }
}

private fun LazyListScope.space(
    modifier: Modifier = Modifier
) {
    item {
        Spacer(modifier = modifier.height(40.dp))
    }
}