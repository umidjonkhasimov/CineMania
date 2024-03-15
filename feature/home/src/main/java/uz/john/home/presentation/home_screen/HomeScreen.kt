package uz.john.home.presentation.home_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.Flow
import uz.john.domain.model.movie.Movie
import uz.john.domain.model.movie.movie_details.Genre
import uz.john.home.R
import uz.john.home.presentation.components.HomeCarouselItem
import uz.john.home.presentation.components.HomeShimmerEffect
import uz.john.paginated_movies_list.AllMoviesScreenParam
import uz.john.ui.components.CineManiaErrorDialog
import uz.john.ui.components.LazyRowItemsHolder
import uz.john.ui.components.MovieCardItem
import uz.john.ui.components.MovieGenreItem
import uz.john.ui.components.SeeAllItem

private val GENRES_HEIGHT = 130.dp
private val SCREEN_PADDING = 16.dp
private val SPACE_BETWEEN_MOVIES = 8.dp
private const val FADE_ANIMATION_DURATION = 200

@Composable
fun HomeScreen(
    onMovieItemClick: (Int) -> Unit,
    onSeeAllClick: (AllMoviesScreenParam) -> Unit
) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    HomeScreenContent(
        uiState = uiState,
        onUiAction = viewModel::onAction,
        sideEffect = viewModel.sideEffect,
        onMovieItemClick = onMovieItemClick,
        onSeeAllClick = onSeeAllClick
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeScreenContract.UiState,
    onUiAction: (HomeScreenContract.UiAction) -> Unit,
    sideEffect: Flow<HomeScreenContract.SideEffect>,
    onMovieItemClick: (Int) -> Unit,
    onSeeAllClick: (AllMoviesScreenParam) -> Unit
) {
    var shouldShowErrorDialog by remember { mutableStateOf(false) }
    var dialogErrorMessage by remember { mutableStateOf("") }

    LaunchedEffect(sideEffect) {
        sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is HomeScreenContract.SideEffect.ShowErrorDialog -> {
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
                onUiAction(HomeScreenContract.UiAction.InitializeScreen)
            },
            onDismissRequest = {
                shouldShowErrorDialog = false
            }
        )
    }

    Scaffold { paddingValues ->
        AnimatedContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            targetState = uiState.isLoading,
            label = "AnimatedContent",
            transitionSpec = {
                (fadeIn(
                    animationSpec = tween(FADE_ANIMATION_DURATION)
                )).togetherWith(
                    fadeOut(animationSpec = tween(FADE_ANIMATION_DURATION))
                )
            }
        ) { targetState ->
            if (targetState)
                HomeShimmerEffect()
            else {
                val pagerState = rememberPagerState { uiState.nowPlayingMovies?.size ?: 0 }

                LazyColumn(
                    modifier = Modifier.padding(top = SCREEN_PADDING)
                ) {
                    trendingTodayPager(
                        pagerState = pagerState,
                        trendingTodayMovies = uiState.trendingTodayMovies,
                        onMovieItemClick = { movieId ->
                            onMovieItemClick(movieId)
                        }
                    )

                    space()

                    nowPlayingMovies(
                        nowPlayingMovies = uiState.nowPlayingMovies,
                        onSeeAllClick = onSeeAllClick,
                        onMovieItemClick = { movieId ->
                            onMovieItemClick(movieId)
                        }
                    )

                    space()

                    popularMovies(
                        popularMovies = uiState.popularMovies,
                        onSeeAllClick = onSeeAllClick,
                        onMovieItemClick = onMovieItemClick
                    )

                    space()

                    allGenres(
                        allGenres = uiState.genres,
                        onGenreClick = onSeeAllClick
                    )

                    space()

                    topRatedMovies(
                        topRatedMovies = uiState.topRated,
                        onSeeAllClick = onSeeAllClick,
                        onMovieItemClick = onMovieItemClick
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.trendingTodayPager(
    pagerState: PagerState,
    trendingTodayMovies: List<Movie>?,
    onMovieItemClick: (Int) -> Unit
) {
    trendingTodayMovies?.let {
        item {
            HomeCarouselItem(
                pagerState = pagerState,
                moviesList = trendingTodayMovies,
                title = stringResource(R.string.trending_today),
                modifier = Modifier,
                onItemClick = { movieId ->
                    onMovieItemClick(movieId)
                }
            )
        }
    }
}

fun LazyListScope.nowPlayingMovies(
    nowPlayingMovies: List<Movie>?,
    onSeeAllClick: (AllMoviesScreenParam) -> Unit,
    onMovieItemClick: (Int) -> Unit
) {
    nowPlayingMovies?.let {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.now_playing),
                shouldShowSeeAllButton = true,
                onSeeAllClick = { onSeeAllClick(AllMoviesScreenParam.NowPlayingMovies) }
            ) {
                LazyRow {
                    items(
                        items = nowPlayingMovies,
                        key = { it.id }
                    ) { movieData ->
                        MovieCardItem(
                            movieData = movieData,
                            onMovieClick = { movieId ->
                                onMovieItemClick(movieId)
                            }
                        )
                        Spacer(modifier = Modifier.width(SPACE_BETWEEN_MOVIES))
                    }
                    item {
                        SeeAllItem(onSeeAllClick = { onSeeAllClick(AllMoviesScreenParam.NowPlayingMovies) })
                    }
                }
            }
        }
    }
}

fun LazyListScope.popularMovies(
    popularMovies: List<Movie>?,
    onSeeAllClick: (AllMoviesScreenParam) -> Unit,
    onMovieItemClick: (Int) -> Unit
) {
    popularMovies?.let {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.popular_movies),
                shouldShowSeeAllButton = true,
                onSeeAllClick = { onSeeAllClick(AllMoviesScreenParam.PopularMovies) }
            ) {
                LazyRow {
                    items(
                        items = popularMovies,
                        key = { it.id }
                    ) { movieData ->
                        MovieCardItem(
                            movieData = movieData,
                            onMovieClick = { movieId ->
                                onMovieItemClick(movieId)
                            }
                        )
                        Spacer(modifier = Modifier.width(SPACE_BETWEEN_MOVIES))
                    }
                    item {
                        SeeAllItem(onSeeAllClick = { onSeeAllClick(AllMoviesScreenParam.PopularMovies) })
                    }
                }
            }
        }
    }
}

fun LazyListScope.allGenres(
    allGenres: List<Genre>?,
    onGenreClick: (AllMoviesScreenParam) -> Unit
) {
    allGenres?.let {
        item {
            LazyRowItemsHolder(
                modifier = Modifier
                    .padding(start = SCREEN_PADDING)
                    .height(GENRES_HEIGHT),
                title = stringResource(R.string.genres),
                shouldShowSeeAllButton = false
            ) {
                LazyRow {
                    items(
                        items = allGenres,
                        key = { it.id }
                    ) { genre ->
                        MovieGenreItem(
                            genre = genre,
                            onGenreClick = { genreId ->
                                onGenreClick(AllMoviesScreenParam.AllMoviesByGenre(genreId = genreId, name = genre.name))
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}

fun LazyListScope.topRatedMovies(
    topRatedMovies: List<Movie>?,
    onSeeAllClick: (AllMoviesScreenParam) -> Unit,
    onMovieItemClick: (Int) -> Unit
) {
    topRatedMovies?.let {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.top_rated),
                shouldShowSeeAllButton = true,
                onSeeAllClick = { onSeeAllClick(AllMoviesScreenParam.TopRated) }
            ) {
                LazyRow {
                    items(
                        items = topRatedMovies,
                        key = { it.id }
                    ) { movieData ->
                        MovieCardItem(
                            movieData = movieData,
                            onMovieClick = { movieId ->
                                onMovieItemClick(movieId)
                            }
                        )
                        Spacer(modifier = Modifier.width(SPACE_BETWEEN_MOVIES))
                    }
                    item {
                        SeeAllItem(onSeeAllClick = { onSeeAllClick(AllMoviesScreenParam.TopRated) })
                    }
                }
            }
        }
    }
}

fun LazyListScope.space(
    modifier: Modifier = Modifier
) {
    item {
        Spacer(modifier = modifier.height(32.dp))
    }
}