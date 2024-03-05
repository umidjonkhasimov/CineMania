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
import androidx.compose.foundation.lazy.LazyRow
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
import uz.john.home.R
import uz.john.home.presentation.home_screen.components.HolderByCategoryItem
import uz.john.home.presentation.home_screen.components.HomeCarouselItem
import uz.john.home.presentation.home_screen.components.HomeShimmerEffect
import uz.john.home.presentation.home_screen.components.MovieItem
import uz.john.ui.components.CineManiaErrorDialog

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
                    modifier = Modifier
                ) {
                    uiState.nowPlayingMovies?.let { nowPlayingMovies ->
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

                    uiState.popularMovies?.let { popularMovies ->
                        item {
                            HolderByCategoryItem(
                                modifier = Modifier.padding(start = 16.dp),
                                title = stringResource(R.string.popular_movies),
                                onSeeAllClick = { }
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
                                            Spacer(modifier = Modifier.width(16.dp))
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    uiState.topRated?.let { topRated ->
                        item {
                            HolderByCategoryItem(
                                modifier = Modifier.padding(start = 16.dp),
                                title = stringResource(R.string.top_rated),
                                onSeeAllClick = { }
                            ) {
                                LazyRow {
                                    topRated.forEach { movieData ->
                                        item {
                                            MovieItem(
                                                movieData = movieData,
                                                onMovieClick = { movieId ->
                                                    onMovieItemClick(movieId)
                                                }
                                            )
                                            Spacer(modifier = Modifier.width(16.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}