package uz.john.cinemania.details_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import uz.john.cinemania.R
import uz.john.cinemania.details_screen.MovieDetailsScreenContract.SideEffect
import uz.john.cinemania.details_screen.MovieDetailsScreenContract.UiAction
import uz.john.cinemania.details_screen.MovieDetailsScreenContract.UiState
import uz.john.cinemania.details_screen.components.CastAndCrewHolderItem
import uz.john.cinemania.details_screen.components.CastItem
import uz.john.cinemania.details_screen.components.CrewItem
import uz.john.cinemania.details_screen.components.ProductionCountryItem
import uz.john.domain.model.NetworkImageSizes
import uz.john.domain.model.movie_details.Cast
import uz.john.domain.model.movie_details.Crew
import uz.john.domain.model.movie_details.MovieDetails
import uz.john.ui.components.CineManiaBackButton
import uz.john.ui.components.CineManiaErrorDialog
import uz.john.ui.components.CineManiaTopBar
import uz.john.ui.components.CoilImage
import uz.john.ui.components.VerticalGradient
import uz.john.ui.theme.CineManiaColors
import uz.john.ui.theme.CineManiaIcons
import uz.john.util.getYear

private const val ANIMATED_CONTENT_DURATION = 200
private val MOVIE_DETAILS_HEIGHT = 500.dp
private val MOVIE_IMAGE_HEIGHT = 300.dp
private val MOVIE_IMAGE_WIDTH = 200.dp
private val SCREEN_PADDING = 16.dp

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    onBackClick: () -> Unit
) {
    val viewModel: MovieDetailsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(movieId) {
        viewModel.onAction(UiAction.GetMovieDetails(movieId))
    }

    MovieDetailsScreenContent(
        uiState = uiState,
        onUiAction = viewModel::onAction,
        sideEffect = viewModel.sideEffect,
        movieId = movieId,
        onBackClick = onBackClick
    )
}

@Composable
private fun MovieDetailsScreenContent(
    uiState: UiState,
    onUiAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
    movieId: Int,
    onBackClick: () -> Unit
) {
    var shouldShowErrorDialog by remember { mutableStateOf(false) }
    var errorDialogMessage by remember { mutableStateOf("") }

    LaunchedEffect(sideEffect) {
        sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SideEffect.ShowErrorDialog -> {
                    shouldShowErrorDialog = true
                    errorDialogMessage = sideEffect.message
                }
            }
        }
    }

    if (shouldShowErrorDialog) {
        CineManiaErrorDialog(
            errorText = errorDialogMessage,
            onCancel = { shouldShowErrorDialog = false },
            onRetry = { onUiAction(UiAction.GetMovieDetails(movieId)) },
            onDismissRequest = { shouldShowErrorDialog = false }
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
                    animationSpec = tween(ANIMATED_CONTENT_DURATION)
                )).togetherWith(
                    fadeOut(animationSpec = tween(ANIMATED_CONTENT_DURATION))
                )
            }
        ) { isLoading ->
            when (isLoading) {
                true -> {
                    MovieDetailsShimmerEffect()
                }

                false -> {
                    uiState.movieDetails?.let { movieDetails ->
                        LazyColumn {
                            movieDetails(
                                movieDetails = movieDetails,
                                onBackClick = onBackClick
                            )

                            movieDescription(
                                movieDetails = movieDetails
                            )

                            cast(
                                castList = movieDetails.credits.cast,
                            )

                            crew(
                                crewList = movieDetails.credits.crew
                            )
                        }
                    } ?: run {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = stringResource(R.string.movie_details_are_not_available))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun LazyListScope.movieDetails(
    movieDetails: MovieDetails,
    onBackClick: () -> Unit
) {
    val ratingColor = if (movieDetails.voteAverage >= 8) CineManiaColors.Orange.primary
    else if (movieDetails.voteAverage >= 7) CineManiaColors.Green.primary
    else CineManiaColors.Gray

    item {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MOVIE_DETAILS_HEIGHT)
        ) {
            CoilImage(
                modifier = Modifier
                    .alpha(.4f)
                    .fillMaxSize(),
                imagePath = movieDetails.posterPath,
                imageSize = NetworkImageSizes.MEDIUM
            )

            VerticalGradient(modifier = Modifier.fillMaxSize())

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CineManiaTopBar(
                    title = movieDetails.title,
                    backgroundColor = Color.Transparent,
                    leadingContent = {
                        CineManiaBackButton(
                            onClick = onBackClick
                        )
                    }
                )

                CoilImage(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .height(MOVIE_IMAGE_HEIGHT)
                        .width(MOVIE_IMAGE_WIDTH),
                    imagePath = movieDetails.posterPath,
                    imageSize = NetworkImageSizes.LARGE
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(CineManiaIcons.Calendar),
                        contentDescription = null
                    )
                    Text(
                        text = movieDetails.releaseDate.getYear(),
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    VerticalDivider(modifier = Modifier.height(12.dp))
                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        painter = painterResource(CineManiaIcons.Duration),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(R.string.duration, movieDetails.runtime),
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    VerticalDivider(modifier = Modifier.height(12.dp))
                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(painter = painterResource(CineManiaIcons.Genre), contentDescription = null)
                    Text(
                        text = movieDetails.genres.first().name,
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(CineManiaIcons.Star),
                        contentDescription = null,
                        tint = ratingColor
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(
                            R.string.reviews,
                            movieDetails.voteAverage,
                            movieDetails.voteCount
                        ),
                        style = MaterialTheme.typography.titleSmall,
                        color = ratingColor
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow {
                    items(
                        items = movieDetails.productionCountries,
                    ) { productionCountry ->
                        ProductionCountryItem(productionCountry)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}

fun LazyListScope.movieDescription(
    movieDetails: MovieDetails,
) {
    var shouldShowMore by mutableStateOf(true)

    item {
        Column(
            modifier = Modifier.padding(horizontal = SCREEN_PADDING)
        ) {
            Text(
                text = movieDetails.tagline,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedVisibility(
                visible = shouldShowMore,
            ) {
                Text(
                    text = movieDetails.overview,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                modifier = Modifier.clickable { shouldShowMore = !shouldShowMore },
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                text = if (shouldShowMore) stringResource(R.string.less) else stringResource(R.string.more)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

fun LazyListScope.cast(
    castList: List<Cast>,
) {
    item {
        CastAndCrewHolderItem(
            modifier = Modifier
                .padding(start = SCREEN_PADDING),
            title = stringResource(R.string.cast),
            onSeeAllClick = { }
        ) {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(3),
            ) {
                items(
                    items = castList,
                    key = { it.id }
                ) { cast ->
                    CastItem(cast = cast)
                }
            }
        }
    }
}

fun LazyListScope.crew(
    crewList: List<Crew>
) {
    item {
        CastAndCrewHolderItem(
            modifier = Modifier
                .padding(start = SCREEN_PADDING),
            title = stringResource(R.string.crew),
            onSeeAllClick = { }
        ) {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(3),
            ) {
                items(
                    items = crewList,
                    key = {
                        it.creditId
                    }
                ) { crew ->
                    CrewItem(crew = crew)
                }
            }
        }
    }
}
