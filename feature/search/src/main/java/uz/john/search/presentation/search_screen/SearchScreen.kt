package uz.john.search.presentation.search_screen

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.Flow
import uz.john.domain.model.movie.Movie
import uz.john.domain.model.person.Person
import uz.john.domain.model.tv_show.TvShow
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenParam
import uz.john.paginated_movies_list.all_people_screen.AllPeopleScreenParam
import uz.john.paginated_movies_list.all_tv_shows_screen.AllTvShowsScreenParam
import uz.john.search.R
import uz.john.search.presentation.search_screen.SearchScreenContract.SideEffect
import uz.john.search.presentation.search_screen.SearchScreenContract.UiAction
import uz.john.search.presentation.search_screen.SearchScreenContract.UiState
import uz.john.search.presentation.search_screen.components.NoSearchResult
import uz.john.ui.components.AnimatedProgressIndicator
import uz.john.ui.components.CineManiaErrorDialog
import uz.john.ui.components.LazyRowItemsHolder
import uz.john.ui.components.MovieCardItem
import uz.john.ui.components.PersonItem
import uz.john.ui.components.SeeAllItem
import uz.john.ui.components.TvShowItem
import uz.john.ui.theme.CineManiaIcons

private val PEOPLE_HEIGHT = 200.dp

@Composable
fun SearchScreen(
    onPersonClick: (Int) -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    onSeeAllMoviesClick: (AllMoviesScreenParam) -> Unit,
    onSeeAllPeopleClick: (AllPeopleScreenParam) -> Unit,
    onSeeAllTvShowsClick: (AllTvShowsScreenParam) -> Unit
) {
    val viewModel: SearchScreenViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    SearchScreenContent(
        uiState = uiState,
        sideEffect = viewModel.sideEffect,
        onUiAction = viewModel::onAction,
        onPersonClick = onPersonClick,
        onMovieClick = onMovieClick,
        onTvShowClick = onTvShowClick,
        onSeeAllMoviesClick = onSeeAllMoviesClick,
        onSeeAllPeopleClick = onSeeAllPeopleClick,
        onSeeAllTvShowsClick = onSeeAllTvShowsClick
    )
}

@Composable
fun SearchScreenContent(
    uiState: UiState,
    sideEffect: Flow<SideEffect>,
    onUiAction: (UiAction) -> Unit,
    onPersonClick: (Int) -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    onSeeAllMoviesClick: (AllMoviesScreenParam) -> Unit,
    onSeeAllPeopleClick: (AllPeopleScreenParam) -> Unit,
    onSeeAllTvShowsClick: (AllTvShowsScreenParam) -> Unit
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
            onRetry = { },
            onDismissRequest = { shouldShowErrorDialog = false }
        )
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            SearchView(
                uiState = uiState,
                onUiAction = onUiAction,
                onPersonClick = onPersonClick,
                onMovieClick = onMovieClick,
                onTvShowClick = onTvShowClick,
                onSeeAllMoviesClick = onSeeAllMoviesClick,
                onSeeAllPeopleClick = onSeeAllPeopleClick,
                onSeeAllTvShowsClick = onSeeAllTvShowsClick
            )

            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 72.dp
                )
            ) {
                people(
                    people = uiState.popularPeople,
                    onSeeAllPeopleClick = { onSeeAllPeopleClick(AllPeopleScreenParam.AllPopularPeople) },
                    onPersonClick = onPersonClick,
                    titleRes = R.string.popular_people
                )

                space()

                movies(
                    movies = uiState.trendingThisWeekMovies,
                    onSeeAllMoviesClick = {
                        onSeeAllMoviesClick(AllMoviesScreenParam.MoviesTrendingThisWeek)
                    },
                    onMovieClick = onMovieClick,
                    titleRes = R.string.movies_trending_this_week
                )

                space()

                tvShows(
                    tvShows = uiState.trendingThisWeekTvShows,
                    onSeeAllTvShowsClick = {
                        onSeeAllTvShowsClick(AllTvShowsScreenParam.TvShowsTrendingThisWeek)
                    },
                    onTvShowClick = onTvShowClick,
                    titleRes = R.string.tv_shows_trending_this_week
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    uiState: UiState,
    onUiAction: (UiAction) -> Unit,
    onPersonClick: (Int) -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    onSeeAllMoviesClick: (AllMoviesScreenParam) -> Unit,
    onSeeAllPeopleClick: (AllPeopleScreenParam) -> Unit,
    onSeeAllTvShowsClick: (AllTvShowsScreenParam) -> Unit,
    modifier: Modifier = Modifier
) {
    var queryText by rememberSaveable { mutableStateOf("") }
    var isSearchBarActive by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .zIndex(1f)
            .fillMaxWidth()
    ) {
        SearchBar(
            modifier = Modifier.align(Alignment.TopCenter),
            query = queryText,
            onQueryChange = {
                queryText = it
                onUiAction(UiAction.SearchForItems(it))
            },
            onSearch = { isSearchBarActive = false },
            active = isSearchBarActive,
            onActiveChange = {
                isSearchBarActive = it
            },
            placeholder = { Text(stringResource(R.string.search_for_movies_tv_shows_people)) },
            leadingIcon = {
                if (isSearchBarActive) {
                    Icon(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false),
                            onClick = {
                                isSearchBarActive = false
                            }
                        ),
                        painter = painterResource(CineManiaIcons.ArrowBack),
                        contentDescription = null
                    )
                } else {
                    Icon(
                        painter = painterResource(CineManiaIcons.Search),
                        contentDescription = null
                    )
                }
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),
                        onClick = {
                            queryText = ""
                            onUiAction(UiAction.ClearSearchResults)
                        }
                    ),
                    painter = painterResource(CineManiaIcons.Clear),
                    contentDescription = null
                )
            },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        ) {
            if (uiState.isLoading) {
                AnimatedProgressIndicator(visible = true)
            }

            if (uiState.moviesResult?.isEmpty() == true
                && uiState.peopleResult?.isEmpty() == true
                && uiState.tvShowResult?.isEmpty() == true
            ) {
                NoSearchResult()
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                people(
                    people = uiState.peopleResult,
                    onPersonClick = onPersonClick,
                    onSeeAllPeopleClick = { onSeeAllPeopleClick(AllPeopleScreenParam.AllPeopleBySearchQuery(queryText)) },
                    titleRes = R.string.people
                )

                movies(
                    movies = uiState.moviesResult,
                    onMovieClick = onMovieClick,
                    onSeeAllMoviesClick = {
                        onSeeAllMoviesClick(AllMoviesScreenParam.AllMoviesBySearchQuery(query = queryText))
                    },
                    titleRes = R.string.movies
                )

                tvShows(
                    tvShows = uiState.tvShowResult,
                    onTvShowClick = onTvShowClick,
                    onSeeAllTvShowsClick = {
                        onSeeAllTvShowsClick(AllTvShowsScreenParam.TvShowsBySearchResult(queryText))
                    },
                    titleRes = R.string.tv_shows
                )
            }
        }
    }
}

fun LazyListScope.people(
    people: List<Person>?,
    onPersonClick: (Int) -> Unit,
    onSeeAllPeopleClick: () -> Unit,
    @StringRes titleRes: Int
) {
    if (!people.isNullOrEmpty()) {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.height(PEOPLE_HEIGHT),
                title = stringResource(titleRes),
                shouldShowSeeAllButton = true,
                onSeeAllClick = onSeeAllPeopleClick
            ) {
                LazyRow {
                    items(
                        items = people,
                        key = { it.id }
                    ) { person ->
                        PersonItem(
                            person = person,
                            onPersonClick = onPersonClick
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    item {
                        SeeAllItem(
                            onSeeAllClick = onSeeAllPeopleClick
                        )
                    }
                }
            }
        }
    }
}

fun LazyListScope.movies(
    movies: List<Movie>?,
    onMovieClick: (Int) -> Unit,
    onSeeAllMoviesClick: () -> Unit,
    @StringRes titleRes: Int
) {
    if (!movies.isNullOrEmpty()) {
        item {
            LazyRowItemsHolder(
                title = stringResource(titleRes),
                shouldShowSeeAllButton = true,
                onSeeAllClick = onSeeAllMoviesClick
            ) {
                LazyRow {
                    items(
                        items = movies,
                        key = { it.id }
                    ) { movie ->
                        MovieCardItem(
                            movieData = movie,
                            onMovieClick = onMovieClick
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    item {
                        SeeAllItem(
                            onSeeAllClick = onSeeAllMoviesClick
                        )
                    }
                }
            }
        }
    }
}

fun LazyListScope.tvShows(
    tvShows: List<TvShow>?,
    onTvShowClick: (Int) -> Unit,
    onSeeAllTvShowsClick: () -> Unit,
    @StringRes titleRes: Int
) {
    if (!tvShows.isNullOrEmpty()) {
        item {
            LazyRowItemsHolder(
                title = stringResource(titleRes),
                shouldShowSeeAllButton = true,
                onSeeAllClick = onSeeAllTvShowsClick
            ) {
                LazyRow {
                    items(
                        items = tvShows,
                        key = { it.id }
                    ) { tvShow ->
                        TvShowItem(
                            tvShow = tvShow,
                            onTvShowClick = onTvShowClick
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    item {
                        SeeAllItem(
                            onSeeAllClick = onSeeAllTvShowsClick
                        )
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
        Spacer(modifier = modifier.height(40.dp))
    }
}