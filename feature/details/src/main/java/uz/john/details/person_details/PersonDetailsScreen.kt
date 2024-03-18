package uz.john.details.person_details

import androidx.compose.animation.core.animateIntAsState
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import uz.john.details.R
import uz.john.details.movie_details_screen.components.PersonCastCreditItem
import uz.john.details.movie_details_screen.components.PersonCrewCreditItem
import uz.john.details.person_details.PersonDetailsScreenContract.SideEffect
import uz.john.details.person_details.PersonDetailsScreenContract.UiAction
import uz.john.details.person_details.PersonDetailsScreenContract.UiState
import uz.john.details.person_details.components.PersonImageItem
import uz.john.domain.model.NetworkImageSizes
import uz.john.domain.model.person.details.PersonDetails
import uz.john.ui.components.CineManiaBackButton
import uz.john.ui.components.CineManiaErrorDialog
import uz.john.ui.components.CineManiaTopBar
import uz.john.ui.components.CoilImage
import uz.john.ui.components.LazyRowItemsHolder

private val PERSON_IMAGE_HEIGHT = 230.dp
private val IMAGES_ROW_HEIGHT = 200.dp
private val PERSON_IMAGE_WIDTH = 150.dp
private val SCREEN_PADDING = 16.dp

@Composable
fun PersonDetailsScreen(
    onBackClick: () -> Unit,
    onImageClick: (String) -> Unit,
    onMovieItemClick: (Int) -> Unit
) {
    val viewModel: PersonDetailsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PersonDetailsScreenContent(
        uiState = uiState,
        onUiAction = viewModel::onAction,
        sideEffect = viewModel.sideEffect,
        onBackClick = onBackClick,
        onImageClick = onImageClick,
        onMovieItemClick = onMovieItemClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailsScreenContent(
    uiState: UiState,
    onUiAction: (UiAction) -> Unit,
    sideEffect: Flow<SideEffect>,
    onBackClick: () -> Unit,
    onImageClick: (String) -> Unit,
    onMovieItemClick: (Int) -> Unit
) {
    var shouldShowErrorDialog by remember { mutableStateOf(false) }
    var errorDialogMessage by remember { mutableStateOf("") }

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
            onRetry = { onUiAction(UiAction.GetPersonDetails) },
            onDismissRequest = { shouldShowErrorDialog = false }
        )
    }

    when (uiState.isLoading) {
        true -> {
            PersonDetailsShimmerEffect()
        }

        false -> {
            uiState.personDetails?.let {
                Scaffold(
                    topBar = {
                        CineManiaTopBar(
                            title = uiState.personDetails.name,
                            leadingContent = {
                                CineManiaBackButton(onClick = onBackClick)
                            }
                        )
                    }
                ) { paddingValues ->
                    LazyColumn(
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        personDetails(
                            personDetails = uiState.personDetails,
                            onImageClick = onImageClick
                        )

                        space()

                        images(
                            personDetails = uiState.personDetails,
                            onImageClick = onImageClick
                        )

                        space()

                        cast(
                            personDetails = uiState.personDetails,
                            onMovieItemClick = onMovieItemClick
                        )

                        space()

                        crew(
                            personDetails = uiState.personDetails,
                            onMovieItemClick = onMovieItemClick
                        )

                        space()
                    }
                }
            } ?: run {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.person_details_are_not_available))
                }
            }
        }
    }
}

private fun LazyListScope.personDetails(
    personDetails: PersonDetails,
    onImageClick: (String) -> Unit
) {
    item {
        var shouldShowMore by remember { mutableStateOf(false) }
        val maxLines by animateIntAsState(targetValue = if (shouldShowMore) 100 else 3, label = "")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SCREEN_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImage(
                modifier = Modifier
                    .height(PERSON_IMAGE_HEIGHT)
                    .width(PERSON_IMAGE_WIDTH)
                    .clip(MaterialTheme.shapes.small)
                    .clickable {
                        personDetails.profilePath?.let(onImageClick)
                    },
                imagePath = personDetails.profilePath,
                imageSize = NetworkImageSizes.MEDIUM
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = personDetails.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.known_for),
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = personDetails.knownForDepartment,
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                personDetails.birthday?.let {
                    Text(
                        text = stringResource(R.string.born),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                personDetails.deathDay?.let {
                    Text(
                        text = stringResource(R.string.passed_away),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.padding(horizontal = SCREEN_PADDING)
        ) {
            Text(
                text = personDetails.biography,
                style = MaterialTheme.typography.titleMedium,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis
            )
            TextButton(
                onClick = {
                    shouldShowMore = !shouldShowMore
                }
            ) {
                Text(
                    text = if (shouldShowMore)
                        stringResource(R.string.less)
                    else
                        stringResource(R.string.more),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

private fun LazyListScope.images(
    personDetails: PersonDetails,
    onImageClick: (String) -> Unit
) {
    if (personDetails.images.profiles.isNotEmpty()) {
        item {
            LazyRowItemsHolder(
                modifier = Modifier
                    .padding(start = SCREEN_PADDING)
                    .height(IMAGES_ROW_HEIGHT),
                title = stringResource(R.string.images),
                shouldShowSeeAllButton = false
            ) {
                LazyRow {
                    items(
                        items = personDetails.images.profiles,
                    ) { profile ->
                        PersonImageItem(
                            image = profile,
                            onImageClick = onImageClick
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}

private fun LazyListScope.cast(
    personDetails: PersonDetails,
    onMovieItemClick: (Int) -> Unit
) {
    if (personDetails.credits.cast.isNotEmpty()) {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.movies_as_an_actor),
                shouldShowSeeAllButton = false
            ) {
                LazyRow {
                    items(
                        items = personDetails.credits.cast,
                        key = { it.creditId }
                    ) { castCredit ->
                        PersonCastCreditItem(
                            movieData = castCredit,
                            onMovieItemClick = onMovieItemClick
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}

private fun LazyListScope.crew(
    personDetails: PersonDetails,
    onMovieItemClick: (Int) -> Unit
) {
    if (personDetails.credits.crew.isNotEmpty()) {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.movies_as_a_crew_member),
                shouldShowSeeAllButton = false
            ) {
                LazyRow {
                    items(
                        items = personDetails.credits.crew,
                        key = { it.creditId }
                    ) { crewData ->
                        PersonCrewCreditItem(
                            movieData = crewData,
                            onMovieItemClick = onMovieItemClick
                        )
                        Spacer(modifier = Modifier.width(8.dp))
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