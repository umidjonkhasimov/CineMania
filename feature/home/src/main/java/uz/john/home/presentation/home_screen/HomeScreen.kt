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
import uz.john.domain.model.Movie
import uz.john.home.R
import uz.john.home.presentation.home_screen.components.HolderByCategoryItem
import uz.john.home.presentation.home_screen.components.HomeCarouselItem
import uz.john.home.presentation.home_screen.components.HomeShimmerEffect
import uz.john.home.presentation.home_screen.components.MovieItem
import uz.john.ui.components.CineManiaErrorDialog
import uz.john.util.logging

private val SCREEN_PADDING = 16.dp
private val SPACE_BETWEEN_MOVIES = 8.dp
private const val FADE_ANIMATION_DURATION = 200

@Composable
fun HomeScreen(
    onMovieItemClick: (Int) -> Unit
) {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    HomeScreenContent(
        uiState = uiState,
        onUiAction = viewModel::onAction,
        sideEffect = viewModel.sideEffect,
        onMovieItemClick = onMovieItemClick
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeScreenContract.UiState,
    onUiAction: (HomeScreenContract.UiAction) -> Unit,
    sideEffect: Flow<HomeScreenContract.SideEffect>,
    onMovieItemClick: (Int) -> Unit
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
                    nowPlayingMoviesPager(
                        pagerState = pagerState,
                        nowPlayingMovies = uiState.nowPlayingMovies,
                        onMovieItemClick = { movieId ->
                            onMovieItemClick(movieId)
                        }
                    )

                    popularMovies(
                        popularMovies = uiState.popularMovies,
                        onSeeAllClick = { },
                        onMovieItemClick = onMovieItemClick
                    )

                    topRatedMovies(
                        topRatedMovies = uiState.topRated,
                        onSeeAllClick = { },
                        onMovieItemClick = onMovieItemClick
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.nowPlayingMoviesPager(
    pagerState: PagerState,
    nowPlayingMovies: List<Movie>?,
    onMovieItemClick: (Int) -> Unit
) {
    nowPlayingMovies?.let {
        item {
            HomeCarouselItem(
                pagerState = pagerState,
                moviesList = nowPlayingMovies,
                modifier = Modifier,
                onItemClick = { movieId ->
                    onMovieItemClick(movieId)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

fun LazyListScope.popularMovies(
    popularMovies: List<Movie>?,
    onSeeAllClick: () -> Unit,
    onMovieItemClick: (Int) -> Unit
) {
    popularMovies?.let {
        item {
            HolderByCategoryItem(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.popular_movies),
                onSeeAllClick = onSeeAllClick
            ) {
                LazyRow {
                    popularMovies.forEach { movieData ->
                        item {
                            MovieItem(
                                movieData = movieData,
                                onMovieClick = { movieId ->
                                    onMovieItemClick(movieId)
                                }
                            )
                            Spacer(modifier = Modifier.width(SPACE_BETWEEN_MOVIES))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

fun LazyListScope.topRatedMovies(
    topRatedMovies: List<Movie>?,
    onSeeAllClick: () -> Unit,
    onMovieItemClick: (Int) -> Unit
) {
    topRatedMovies?.let {
        item {
            HolderByCategoryItem(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.top_rated),
                onSeeAllClick = onSeeAllClick
            ) {
                LazyRow {
                    topRatedMovies.forEach { movieData ->
                        item {
                            MovieItem(
                                movieData = movieData,
                                onMovieClick = { movieId ->
                                    onMovieItemClick(movieId)
                                }
                            )
                            Spacer(modifier = Modifier.width(SPACE_BETWEEN_MOVIES))
                        }
                    }
                }
            }
        }
    }
}