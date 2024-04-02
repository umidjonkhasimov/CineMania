package uz.john.details.tv_show_details

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
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
import uz.john.details.tv_show_details.TvShowDetailsScreenContract.SideEffect
import uz.john.details.tv_show_details.TvShowDetailsScreenContract.UiAction
import uz.john.details.tv_show_details.TvShowDetailsScreenContract.UiState
import uz.john.details.tv_show_details.components.TvShowCastItem
import uz.john.details.tv_show_details.components.TvShowCrewItem
import uz.john.details.tv_show_details.components.TvShowDetailsShimmerEffect
import uz.john.domain.model.NetworkImageSizes
import uz.john.domain.model.common.ImagesResponse
import uz.john.domain.model.common.Video
import uz.john.domain.model.tv_show.TvShow
import uz.john.domain.model.tv_show.tv_show_details.TvShowCast
import uz.john.domain.model.tv_show.tv_show_details.TvShowCrew
import uz.john.domain.model.tv_show.tv_show_details.TvShowDetails
import uz.john.paginated_movies_list.all_tv_shows_screen.AllTvShowsScreenParam
import uz.john.ui.components.CineManiaBackButton
import uz.john.ui.components.CineManiaErrorDialog
import uz.john.ui.components.CineManiaTopBar
import uz.john.ui.components.CoilImage
import uz.john.ui.components.LazyRowItemsHolder
import uz.john.ui.components.SeeAllItem
import uz.john.ui.components.TvShowItem
import uz.john.ui.components.VerticalGradient
import uz.john.ui.theme.CineManiaColors
import uz.john.ui.theme.CineManiaIcons
import uz.john.util.getYear

private const val ANIMATED_CONTENT_DURATION = 200
private val TV_SHOW_DETAILS_HEIGHT = 400.dp
private val TV_SHOW_IMAGE_HEIGHT = 200.dp
private val TV_SHOW_IMAGE_WIDTH = 300.dp
private val SCREEN_PADDING = 16.dp

@Composable
fun TvShowDetailsScreen(
    onBackClick: () -> Unit,
    onImageClick: (String) -> Unit,
    onTvShowClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit,
    onSeeAllTvShowsClick: (AllTvShowsScreenParam) -> Unit,
    onSignInClick: () -> Unit
) {
    val viewModel: TvShowDetailsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TvShowDetailsScreenContent(
        uiState = uiState,
        sideEffect = viewModel.sideEffect,
        onUiAction = viewModel::onAction,
        onBackClick = onBackClick,
        onImageClick = onImageClick,
        onTvShowClick = onTvShowClick,
        onPersonClick = onPersonClick,
        onSeeAllTvShowsClick = onSeeAllTvShowsClick,
        onSignInClick = onSignInClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvShowDetailsScreenContent(
    uiState: UiState,
    sideEffect: Flow<SideEffect>,
    onUiAction: (UiAction) -> Unit,
    onBackClick: () -> Unit,
    onImageClick: (String) -> Unit,
    onTvShowClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit,
    onSeeAllTvShowsClick: (AllTvShowsScreenParam) -> Unit,
    onSignInClick: () -> Unit
) {
    var shouldShowErrorDialog by remember { mutableStateOf(false) }
    var errorDialogMessage by remember { mutableStateOf("") }
    var shouldShowBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(sideEffect) {
        sideEffect.collect {
            when (it) {
                is SideEffect.ShowErrorDialog -> {
                    shouldShowErrorDialog = true
                    errorDialogMessage = it.errorMessage
                }
            }
        }
    }

    if (shouldShowErrorDialog) {
        CineManiaErrorDialog(
            errorText = errorDialogMessage,
            onCancel = { shouldShowErrorDialog = false },
            onRetry = { onUiAction(UiAction.InitializeScreen) },
            onDismissRequest = { shouldShowErrorDialog = false }
        )
    }

    if (shouldShowBottomSheet) {
        uiState.tvShowDetails?.let {
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
                                    onUiAction(UiAction.SetTvShowFavorite(tvShowId = uiState.tvShowDetails.id))
                                }
                                .padding(horizontal = SCREEN_PADDING),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (uiState.tvShowDetails.isFavorite) {
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
                                    onUiAction(UiAction.SetTvShowWatchLater(tvShowId = uiState.tvShowDetails.id))
                                }
                                .padding(horizontal = SCREEN_PADDING),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (uiState.tvShowDetails.isWatchLater) {
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
                    TvShowDetailsShimmerEffect()
                }

                false -> {
                    val context = LocalContext.current

                    uiState.tvShowDetails?.let { tvShowDetails ->
                        LazyColumn {
                            tvShowDetails(
                                tvShowDetails = tvShowDetails,
                                onBackClick = onBackClick,
                                onImageClick = onImageClick,
                                onMoreClick = {
                                    shouldShowBottomSheet = true
                                }
                            )

                            space()

                            cast(
                                castList = tvShowDetails.credits.cast,
                                onPersonClick = onPersonClick
                            )

                            space()

                            crew(
                                crewList = tvShowDetails.credits.crew,
                                onPersonClick = onPersonClick
                            )

                            space()

                            images(
                                images = tvShowDetails.images,
                                onImageClick = onImageClick
                            )

                            space()

                            videos(
                                videoList = tvShowDetails.videos.videoList,
                                onVideoClick = { videoKey ->
                                    launchYoutube(videoKey, context)
                                }
                            )

                            space()

                            recommendedTvShows(
                                recommendedTvShows = uiState.recommendedTvShows,
                                onTvShowClick = onTvShowClick,
                                onSeeAllSimilarClick = {
                                    onSeeAllTvShowsClick(AllTvShowsScreenParam.RecommendedTvShows(tvShowDetails.id))
                                }
                            )

                            space()

                            similarTvShows(
                                similarTvShows = uiState.similarTvShows,
                                onTvShowClick = onTvShowClick,
                                onSeeAllSimilarClick = {
                                    onSeeAllTvShowsClick(AllTvShowsScreenParam.SimilarTvShows(tvShowDetails.id))
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
private fun LazyListScope.tvShowDetails(
    tvShowDetails: TvShowDetails,
    onBackClick: () -> Unit,
    onImageClick: (String) -> Unit,
    onMoreClick: () -> Unit
) {
    val ratingColor = if (tvShowDetails.voteAverage >= 8) CineManiaColors.Orange.primary
    else if (tvShowDetails.voteAverage >= 7) CineManiaColors.Green.primary
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
                    .height(TV_SHOW_DETAILS_HEIGHT),
                imagePath = tvShowDetails.posterPath,
                imageSize = NetworkImageSizes.MEDIUM
            )

            VerticalGradient(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(TV_SHOW_DETAILS_HEIGHT)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CineManiaTopBar(
                    title = tvShowDetails.name,
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
                        .height(TV_SHOW_IMAGE_HEIGHT)
                        .width(TV_SHOW_IMAGE_WIDTH)
                        .clickable {
                            tvShowDetails.backdropPath?.let { onImageClick(it) }
                        },
                    imagePath = tvShowDetails.backdropPath,
                    imageSize = NetworkImageSizes.LARGE
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    val firstAirYear = tvShowDetails.firstAirDate.getYear()
                    val lastAirYear = tvShowDetails.lastAirDate?.getYear()

                    Icon(
                        painter = painterResource(CineManiaIcons.Calendar),
                        contentDescription = null
                    )
                    Text(
                        text = if (tvShowDetails.inProduction) {
                            firstAirYear
                        } else {
                            if (lastAirYear == null || firstAirYear == lastAirYear)
                                tvShowDetails.firstAirDate.getYear()
                            else
                                "${tvShowDetails.firstAirDate.getYear()}-${tvShowDetails.lastAirDate?.getYear()}"
                        },
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    VerticalDivider(modifier = Modifier.height(12.dp))
                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(painter = painterResource(CineManiaIcons.Genre), contentDescription = null)
                    Text(
                        text = tvShowDetails.genres.firstOrNull()?.name ?: "",
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraLarge)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(CineManiaIcons.Duration),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(
                            R.string.season_and_episodes_count,
                            tvShowDetails.numberOfSeasons,
                            tvShowDetails.numberOfEpisodes
                        ),
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
                            tvShowDetails.voteAverage,
                            tvShowDetails.voteCount
                        ),
                        style = MaterialTheme.typography.titleSmall,
                        color = ratingColor
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow {
                    items(
                        items = tvShowDetails.productionCountries,
                    ) { productionCountry ->
                        ProductionCountryItem(productionCountry)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .padding(horizontal = SCREEN_PADDING)
                        .align(Alignment.Start)
                ) {
                    Text(
                        text = tvShowDetails.tagline,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AnimatedVisibility(
                        visible = shouldShowMore,
                    ) {
                        Text(
                            text = tvShowDetails.overview,
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
    castList: List<TvShowCast>,
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
                        TvShowCastItem(
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
    crewList: List<TvShowCrew>,
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
                        TvShowCrewItem(
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

private fun LazyListScope.recommendedTvShows(
    recommendedTvShows: List<TvShow>?,
    onTvShowClick: (Int) -> Unit,
    onSeeAllSimilarClick: () -> Unit
) {
    if (!recommendedTvShows.isNullOrEmpty()) {
        item {
            LazyRowItemsHolder(
                modifier = Modifier
                    .padding(start = SCREEN_PADDING),
                title = stringResource(R.string.recommended),
                shouldShowSeeAllButton = true,
                onSeeAllClick = onSeeAllSimilarClick
            ) {
                LazyRow {
                    items(
                        items = recommendedTvShows,
                        key = { it.id }
                    ) { tvShow ->
                        TvShowItem(tvShow = tvShow, onTvShowClick = onTvShowClick)

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

private fun LazyListScope.similarTvShows(
    similarTvShows: List<TvShow>?,
    onTvShowClick: (Int) -> Unit,
    onSeeAllSimilarClick: () -> Unit
) {
    if (!similarTvShows.isNullOrEmpty()) {
        item {
            LazyRowItemsHolder(
                modifier = Modifier
                    .padding(start = SCREEN_PADDING),
                title = stringResource(R.string.similar_tv_shows),
                shouldShowSeeAllButton = true,
                onSeeAllClick = onSeeAllSimilarClick
            ) {
                LazyRow {
                    items(
                        items = similarTvShows,
                        key = { it.id }
                    ) { tvShow ->
                        TvShowItem(tvShow = tvShow, onTvShowClick = onTvShowClick)

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