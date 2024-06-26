package uz.john.details.movie_details_screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import uz.john.details.R
import uz.john.details.components.ImageItem
import uz.john.details.components.ProductionCountryItem
import uz.john.details.components.VideoItem
import uz.john.details.movie_details_screen.MovieDetailsScreenContract.SideEffect
import uz.john.details.movie_details_screen.MovieDetailsScreenContract.UiAction
import uz.john.details.movie_details_screen.MovieDetailsScreenContract.UiState
import uz.john.details.movie_details_screen.components.CastItem
import uz.john.details.movie_details_screen.components.CrewItem
import uz.john.domain.model.NetworkImageSizes
import uz.john.domain.model.common.ImagesResponse
import uz.john.domain.model.common.Video
import uz.john.domain.model.movie.Movie
import uz.john.domain.model.movie.movie_details.Cast
import uz.john.domain.model.movie.movie_details.Crew
import uz.john.domain.model.movie.movie_details.MovieDetails
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenParam
import uz.john.ui.components.CineManiaBackButton
import uz.john.ui.components.CineManiaErrorDialog
import uz.john.ui.components.CineManiaTopBar
import uz.john.ui.components.CoilImage
import uz.john.ui.components.LazyRowItemsHolder
import uz.john.ui.components.MovieCardItem
import uz.john.ui.components.SeeAllItem
import uz.john.ui.components.VerticalGradient
import uz.john.ui.theme.CineManiaColors
import uz.john.ui.theme.CineManiaIcons
import uz.john.util.formatWithCommaSeparators
import uz.john.util.getYear

private const val ANIMATED_CONTENT_DURATION = 200
private val MOVIE_DETAILS_HEIGHT = 500.dp
private val MOVIE_IMAGE_HEIGHT = 300.dp
private val MOVIE_IMAGE_WIDTH = 200.dp
private val SCREEN_PADDING = 16.dp

@Composable
fun MovieDetailsScreen(
    onBackClick: () -> Unit,
    onImageClick: (String) -> Unit,
    onMovieClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit,
    onSeeAllSimilarClick: (AllMoviesScreenParam) -> Unit,
    onSignInClick: () -> Unit
) {
    val viewModel: MovieDetailsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MovieDetailsScreenContent(
        uiState = uiState,
        onUiAction = viewModel::onAction,
        sideEffect = viewModel.sideEffect,
        onBackClick = onBackClick,
        onImageClick = onImageClick,
        onMovieClick = onMovieClick,
        onPersonClick = onPersonClick,
        onSeeAllSimilarClick = onSeeAllSimilarClick,
        onSignInClick = onSignInClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetailsScreenContent(
    uiState: UiState,
    onUiAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
    onBackClick: () -> Unit,
    onImageClick: (String) -> Unit,
    onMovieClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit,
    onSeeAllSimilarClick: (AllMoviesScreenParam) -> Unit,
    onSignInClick: () -> Unit
) {
    var shouldShowErrorDialog by remember { mutableStateOf(false) }
    var errorDialogMessage by remember { mutableStateOf("") }
    var shouldShowBottomSheet by remember { mutableStateOf(false) }

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
            onRetry = { onUiAction(UiAction.GetMovieDetails) },
            onDismissRequest = { shouldShowErrorDialog = false }
        )
    }

    if (shouldShowBottomSheet) {
        uiState.movieDetails?.let {
            ModalBottomSheet(
                onDismissRequest = { shouldShowBottomSheet = false },
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                if (uiState.isLoggedIn) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clickable {
                                    onUiAction(UiAction.SetMovieFavorite(movieId = uiState.movieDetails.id))
                                }
                                .padding(horizontal = SCREEN_PADDING),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (uiState.movieDetails.isFavorite) {
                                Icon(
                                    painter = painterResource(CineManiaIcons.Filled.Favorite),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            } else {
                                Icon(
                                    painter = painterResource(CineManiaIcons.Favorite),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = stringResource(R.string.add_to_favorite)
                            )
                        }

                        HorizontalDivider(
                            modifier = Modifier
                                .padding(start = SCREEN_PADDING),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.surfaceVariant
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clickable {
                                    onUiAction(UiAction.SetMovieWatchLater(movieId = uiState.movieDetails.id))
                                }
                                .padding(horizontal = SCREEN_PADDING),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (uiState.movieDetails.isWatchLater) {
                                Icon(
                                    painter = painterResource(CineManiaIcons.Filled.Clock),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            } else {
                                Icon(
                                    painter = painterResource(CineManiaIcons.Clock),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = stringResource(R.string.add_to_watch_later)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                } else {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.sign_in_to_your_account),
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = {
                                onSignInClick()
                                shouldShowBottomSheet = false
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.sign_in),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
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
                    val context = LocalContext.current

                    uiState.movieDetails?.let { movieDetails ->
                        LazyColumn {
                            movieDetails(
                                movieDetails = movieDetails,
                                onBackClick = onBackClick,
                                onImageClick = onImageClick,
                                onMoreClick = {
                                    shouldShowBottomSheet = true
                                }
                            )

                            space()

                            cast(
                                castList = movieDetails.credits.cast,
                                onPersonClick = onPersonClick
                            )

                            space()

                            crew(
                                crewList = movieDetails.credits.crew,
                                onPersonClick = onPersonClick
                            )

                            space()

                            images(
                                images = movieDetails.images,
                                onImageClick = onImageClick
                            )

                            space()

                            videos(
                                videoList = movieDetails.videos.videoList,
                                onVideoClick = { videoKey ->
                                    launchYoutube(videoKey, context)
                                }
                            )

                            space()

                            recommendedMovies(
                                recommendedMovies = uiState.recommendedMovies,
                                onMovieClick = onMovieClick,
                                onSeeAllSimilarClick = {
                                    onSeeAllSimilarClick(AllMoviesScreenParam.RecommendedMovies(movieDetails.id))
                                }
                            )

                            space()

                            similarMovies(
                                similarMovies = uiState.similarMovies,
                                onMovieClick = onMovieClick,
                                onSeeAllSimilarClick = {
                                    onSeeAllSimilarClick(AllMoviesScreenParam.SimilarMovies(movieDetails.id))
                                }
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
private fun LazyListScope.movieDetails(
    movieDetails: MovieDetails,
    onBackClick: () -> Unit,
    onImageClick: (String) -> Unit,
    onMoreClick: () -> Unit
) {
    val ratingColor = if (movieDetails.voteAverage >= 8) CineManiaColors.Orange.primary
    else if (movieDetails.voteAverage >= 7) CineManiaColors.Green.primary
    else CineManiaColors.Gray
    var shouldShowMore by mutableStateOf(true)

    item {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CoilImage(
                modifier = Modifier
                    .alpha(.4f)
                    .fillMaxWidth()
                    .height(MOVIE_DETAILS_HEIGHT),
                imagePath = movieDetails.posterPath,
                imageSize = NetworkImageSizes.MEDIUM
            )

            VerticalGradient(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MOVIE_DETAILS_HEIGHT)
            )

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
                    },
                    trailingContent = {
                        Icon(
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(bounded = false)
                                ) {
                                    onMoreClick()
                                },
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = null
                        )
                    }
                )

                CoilImage(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .height(MOVIE_IMAGE_HEIGHT)
                        .width(MOVIE_IMAGE_WIDTH)
                        .clickable {
                            movieDetails.posterPath?.let { onImageClick(it) }
                        },
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
                        text = movieDetails.genres.firstOrNull()?.name ?: "",
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
                        painter = painterResource(CineManiaIcons.DollarSign),
                        contentDescription = null
                    )
                    Text(
                        text = movieDetails.revenue.formatWithCommaSeparators(),
                        style = MaterialTheme.typography.titleSmall,
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

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .padding(horizontal = SCREEN_PADDING)
                        .align(Alignment.Start)
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
            }
        }
    }
}

private fun LazyListScope.cast(
    castList: List<Cast>,
    onPersonClick: (Int) -> Unit,
) {
    if (castList.isNotEmpty()) {
        item {
            LazyRowItemsHolder(
                modifier = Modifier
                    .padding(start = SCREEN_PADDING),
                title = stringResource(R.string.cast),
                shouldShowSeeAllButton = false
            ) {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = castList,
                        key = { it.id }
                    ) { cast ->
                        CastItem(
                            cast = cast,
                            onPersonClick = onPersonClick
                        )
                    }
                }
            }
        }
    }
}

private fun LazyListScope.crew(
    crewList: List<Crew>,
    onPersonClick: (Int) -> Unit,
) {
    if (crewList.isNotEmpty()) {
        item {
            LazyRowItemsHolder(
                modifier = Modifier
                    .padding(start = SCREEN_PADDING),
                title = stringResource(R.string.crew),
                shouldShowSeeAllButton = false
            ) {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = crewList,
                        key = {
                            it.creditId
                        }
                    ) { crew ->
                        CrewItem(
                            crew = crew,
                            onPersonClick = onPersonClick
                        )
                    }
                }
            }
        }
    }
}

private fun LazyListScope.images(
    images: ImagesResponse,
    onImageClick: (String) -> Unit
) {
    if (images.backdrops.isNotEmpty()) {
        item {
            LazyRowItemsHolder(
                modifier = Modifier
                    .height(200.dp)
                    .padding(start = SCREEN_PADDING),
                title = stringResource(R.string.images),
                shouldShowSeeAllButton = false
            ) {
                LazyRow {
                    items(
                        items = images.backdrops
                    ) { image ->
                        ImageItem(
                            image = image,
                            onImageClick = onImageClick
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}

private fun LazyListScope.videos(
    videoList: List<Video>,
    onVideoClick: (String) -> Unit
) {
    if (videoList.isNotEmpty()) {
        item {
            LazyRowItemsHolder(
                modifier = Modifier
                    .height(200.dp)
                    .padding(start = SCREEN_PADDING),
                title = stringResource(R.string.trailers_teasers),
                shouldShowSeeAllButton = false
            ) {
                LazyRow {
                    items(
                        items = videoList,
                        key = { it.id }
                    ) { video ->
                        VideoItem(
                            video = video,
                            onVideoClick = onVideoClick
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}

private fun LazyListScope.recommendedMovies(
    recommendedMovies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    onSeeAllSimilarClick: () -> Unit
) {
    if (recommendedMovies.isNotEmpty()) {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.recommended),
                shouldShowSeeAllButton = true,
                onSeeAllClick = onSeeAllSimilarClick
            ) {
                LazyRow {
                    items(
                        items = recommendedMovies,
                        key = { it.id }
                    ) { movie ->
                        MovieCardItem(
                            movieData = movie,
                            onMovieClick = onMovieClick
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    item {
                        SeeAllItem(onSeeAllClick = onSeeAllSimilarClick)
                    }
                }
            }
        }
    }
}

private fun LazyListScope.similarMovies(
    similarMovies: List<Movie>,
    onMovieClick: (Int) -> Unit,
    onSeeAllSimilarClick: () -> Unit
) {
    if (similarMovies.isNotEmpty()) {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.similar_movies),
                shouldShowSeeAllButton = true,
                onSeeAllClick = onSeeAllSimilarClick
            ) {
                LazyRow {
                    items(
                        items = similarMovies,
                        key = { it.id }
                    ) { movie ->
                        MovieCardItem(
                            movieData = movie,
                            onMovieClick = onMovieClick
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    item {
                        SeeAllItem(onSeeAllClick = onSeeAllSimilarClick)
                    }
                }
            }
        }
    }
}

private fun LazyListScope.space(modifier: Modifier = Modifier) {
    item {
        Spacer(modifier = modifier.height(40.dp))
    }
}

private fun launchYoutube(videoKey: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("https://www.youtube.com/watch?v=$videoKey")
    }

    context.startActivity(intent)
}