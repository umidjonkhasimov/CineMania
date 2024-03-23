package uz.john.paginated_movies_list.all_tv_shows_screen

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow
import uz.john.domain.model.tv_show.TvShow
import uz.john.paginated_movies_list.all_tv_shows_screen.AllTvShowsScreenContract.SideEffect
import uz.john.paginated_movies_list.all_tv_shows_screen.AllTvShowsScreenContract.UiAction
import uz.john.paginated_movies_list.all_tv_shows_screen.AllTvShowsScreenContract.UiState
import uz.john.ui.components.AnimatedProgressIndicator
import uz.john.ui.components.CineManiaBackButton
import uz.john.ui.components.CineManiaErrorDialog
import uz.john.ui.components.CineManiaTopBar
import uz.john.ui.components.PagingErrorItem
import uz.john.ui.components.TvShowItem

private val MIN_TV_SHOW_WIDTH = 150.dp
private val SCREEN_PADDING = 16.dp

@Composable
fun AllTvShowsScreen(
    onBackClick: () -> Unit,
    onTvShowClick: (Int) -> Unit
) {
    val viewModel: AllTvShowsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagingData = viewModel.paginatedItems.collectAsLazyPagingItems()

    ScreenContent(
        uiState = uiState,
        pagingData = pagingData,
        sideEffect = viewModel.sideEffect,
        onUiAction = viewModel::onAction,
        onBackClick = onBackClick,
        onTvShowClick = onTvShowClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(
    uiState: UiState,
    pagingData: LazyPagingItems<TvShow>,
    sideEffect: Flow<SideEffect>,
    onUiAction: (UiAction) -> Unit,
    onBackClick: () -> Unit,
    onTvShowClick: (Int) -> Unit
) {
    var shouldShowErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    if (shouldShowErrorDialog) {
        CineManiaErrorDialog(
            errorText = errorMessage,
            onCancel = {
                shouldShowErrorDialog = false
            },
            onRetry = {
                shouldShowErrorDialog = false
                pagingData.refresh()
            },
            onDismissRequest = {
                shouldShowErrorDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            CineManiaTopBar(
                title = uiState.title ?: "",
                leadingContent = {
                    CineManiaBackButton(onClick = onBackClick)
                }
            )
        }
    ) { paddingValues ->
        when (pagingData.loadState.refresh) {
            is LoadState.Error -> {
                shouldShowErrorDialog = true
                errorMessage = (pagingData.loadState.refresh as LoadState.Error).error.message.toString()
            }

            LoadState.Loading -> {
                AllTvShowsShimmerEffect(modifier = Modifier.padding(paddingValues))
            }

            is LoadState.NotLoading -> Unit
        }

        LazyVerticalGrid(
            modifier = Modifier.padding(paddingValues),
            columns = GridCells.Adaptive(MIN_TV_SHOW_WIDTH),
            contentPadding = PaddingValues(horizontal = SCREEN_PADDING),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                count = pagingData.itemCount
            ) { index ->
                val tvShow = pagingData[index]
                tvShow?.let {
                    TvShowItem(
                        tvShow = tvShow,
                        onTvShowClick = onTvShowClick
                    )
                }
            }

            item(
                span = { GridItemSpan(maxCurrentLineSpan) }
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
