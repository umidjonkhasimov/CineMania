package uz.john.paginated_movies_list.all_movies_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow
import uz.john.domain.model.movie.Movie
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenContract.UiAction
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenContract.UiState
import uz.john.ui.components.AnimatedProgressIndicator
import uz.john.ui.components.CineManiaBackButton
import uz.john.ui.components.CineManiaErrorDialog
import uz.john.ui.components.CineManiaTopBar
import uz.john.ui.components.MovieCardItem
import uz.john.ui.components.PagingErrorItem

private const val GRID_CELL_COUNT = 2
private val SCREEN_PADDING = 16.dp

@Composable
fun AllMoviesScreen(
    onMovieItemClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel: AllMoviesViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val pagingData = viewModel.paginatedMovies.collectAsLazyPagingItems()

    AllMoviesScreenContent(
        uiState = uiState,
        pagingData = pagingData,
        onUiAction = viewModel::onAction,
        sideEffect = viewModel.sideEffect,
        onMovieItemClick = onMovieItemClick,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllMoviesScreenContent(
    uiState: UiState,
    pagingData: LazyPagingItems<Movie>,
    onUiAction: (UiAction) -> Unit,
    sideEffect: Flow<AllMoviesScreenContract.SideEffect>,
    onMovieItemClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    var shouldShowErrorDialog by remember { mutableStateOf(false) }
    var errorDialogMessage by remember { mutableStateOf("") }

    if (shouldShowErrorDialog) {
        CineManiaErrorDialog(
            errorText = errorDialogMessage,
            onCancel = { shouldShowErrorDialog = false },
            onRetry = {
                pagingData.retry()
                shouldShowErrorDialog = false
            },
            onDismissRequest = { shouldShowErrorDialog = false }
        )
    }

    Scaffold(
        topBar = {
            uiState.title?.let { title ->
                CineManiaTopBar(
                    title = title,
                    leadingContent = {
                        CineManiaBackButton(
                            onClick = onBackClick
                        )
                    }
                )
            }
        }
    ) { paddingValues ->
        when (pagingData.loadState.refresh) {
            is LoadState.Error -> {
                shouldShowErrorDialog = true
                errorDialogMessage = (pagingData.loadState.refresh as LoadState.Error).error.message.toString()
            }

            LoadState.Loading -> {
                AllMoviesShimmerEffect(
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is LoadState.NotLoading -> Unit
        }

        LazyVerticalGrid(
            modifier = Modifier.padding(paddingValues),
            columns = GridCells.Fixed(GRID_CELL_COUNT),
            horizontalArrangement = Arrangement.spacedBy(SCREEN_PADDING),
            verticalArrangement = Arrangement.spacedBy(SCREEN_PADDING),
            contentPadding = PaddingValues(horizontal = SCREEN_PADDING),
        ) {
            items(
                count = pagingData.itemCount,
                key = { it }
            ) { index ->
                val movieData = pagingData[index]
                movieData?.let {
                    MovieCardItem(
                        movieData = movieData,
                        onMovieClick = onMovieItemClick
                    )
                }
            }
            item(
                span = { GridItemSpan(GRID_CELL_COUNT) }
            ) {
                when (pagingData.loadState.append) {
                    is LoadState.Error -> {
                        PagingErrorItem(
                            errorText = (pagingData.loadState.append as LoadState.Error).error.message,
                            onRetryClick = { pagingData.retry() }
                        )
                    }

                    LoadState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            AnimatedProgressIndicator(visible = true)
                        }
                    }

                    is LoadState.NotLoading -> Unit
                }
            }
        }
    }
}