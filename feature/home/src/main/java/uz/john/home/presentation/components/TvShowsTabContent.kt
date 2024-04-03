package uz.john.home.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import uz.john.domain.model.common.Genre
import uz.john.domain.model.tv_show.TvShow
import uz.john.home.R
import uz.john.home.presentation.home_screen.HomeScreenContract.UiAction
import uz.john.home.presentation.home_screen.HomeScreenContract.UiState
import uz.john.paginated_movies_list.all_tv_shows_screen.AllTvShowsScreenParam
import uz.john.ui.components.LazyRowItemsHolder
import uz.john.ui.components.MediaGenreItem
import uz.john.ui.components.SeeAllItem
import uz.john.ui.components.TvShowItem

private val SCREEN_PADDING = 16.dp
private val GENRES_HEIGHT = 120.dp
private val SPACE_BETWEEN_MOVIES = 8.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TvShowsTabContent(
    uiState: UiState,
    onTvShowClick: (Int) -> Unit,
    onSeeAllClick: (AllTvShowsScreenParam) -> Unit
) {
    val pagerState = rememberPagerState { uiState.nowPlayingMovies?.size ?: 0 }

    LazyColumn(
        modifier = Modifier.padding(top = SCREEN_PADDING)
    ) {
        trendingTodayTvShowsPager(
            pagerState = pagerState,
            trendingTodayTvShows = uiState.trendingTodayTvShows,
            onTvShowClick = onTvShowClick
        )

        space()

        nowPlayingTvShows(
            nowPlayingTvShows = uiState.nowPlayingTvShows,
            onSeeAllClick = onSeeAllClick,
            onTvShowClick = onTvShowClick
        )

        space()

        popularTvShows(
            popularTvShows = uiState.popularTvShows,
            onSeeAllClick = onSeeAllClick,
            onTvShowClick = onTvShowClick
        )

        space()

        allGenres(
            allGenres = uiState.tvShowGenres,
            onGenreClick = onSeeAllClick
        )

        space()

        topRatedTvShows(
            topRatedTvShows = uiState.topRatedTvShows,
            onSeeAllClick = onSeeAllClick,
            onTvShowClick = onTvShowClick
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.trendingTodayTvShowsPager(
    pagerState: PagerState,
    trendingTodayTvShows: List<TvShow>?,
    onTvShowClick: (Int) -> Unit
) {
    trendingTodayTvShows?.let {
        item {
            TvShowsCarouselItem(
                pagerState = pagerState,
                tvShowList = trendingTodayTvShows,
                title = stringResource(R.string.trending_today),
                modifier = Modifier,
                onItemClick = { tvShowId ->
                    onTvShowClick(tvShowId)
                }
            )
        }
    }
}

private fun LazyListScope.nowPlayingTvShows(
    nowPlayingTvShows: List<TvShow>?,
    onSeeAllClick: (AllTvShowsScreenParam) -> Unit,
    onTvShowClick: (Int) -> Unit
) {
    nowPlayingTvShows?.let {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.now_playing),
                shouldShowSeeAllButton = true,
                onSeeAllClick = { onSeeAllClick(AllTvShowsScreenParam.AllNowPlayingTvShows) }
            ) {
                LazyRow {
                    items(
                        items = nowPlayingTvShows,
                        key = { it.id }
                    ) { tvShow ->
                        TvShowItem(tvShow = tvShow, onTvShowClick = onTvShowClick)
                        Spacer(modifier = Modifier.width(SPACE_BETWEEN_MOVIES))
                    }
                    item {
                        SeeAllItem(onSeeAllClick = { onSeeAllClick(AllTvShowsScreenParam.AllNowPlayingTvShows) })
                    }
                }
            }
        }
    }
}

private fun LazyListScope.popularTvShows(
    popularTvShows: List<TvShow>?,
    onSeeAllClick: (AllTvShowsScreenParam) -> Unit,
    onTvShowClick: (Int) -> Unit
) {
    popularTvShows?.let {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.popular_tv_shows),
                shouldShowSeeAllButton = true,
                onSeeAllClick = { onSeeAllClick(AllTvShowsScreenParam.AllPopularTvShows) }
            ) {
                LazyRow {
                    items(
                        items = popularTvShows,
                        key = { it.id }
                    ) { tvShow ->
                        TvShowItem(
                            tvShow = tvShow,
                            onTvShowClick = onTvShowClick
                        )
                        Spacer(modifier = Modifier.width(SPACE_BETWEEN_MOVIES))
                    }
                    item {
                        SeeAllItem(onSeeAllClick = { onSeeAllClick(AllTvShowsScreenParam.AllPopularTvShows) })
                    }
                }
            }
        }
    }
}

private fun LazyListScope.allGenres(
    allGenres: List<Genre>?,
    onGenreClick: (AllTvShowsScreenParam) -> Unit
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
                        MediaGenreItem(
                            genre = genre,
                            onGenreClick = { genreId ->
                                onGenreClick(AllTvShowsScreenParam.AllTvShowsByGenre(genreId = genreId, name = genre.name))
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}

private fun LazyListScope.topRatedTvShows(
    topRatedTvShows: List<TvShow>?,
    onSeeAllClick: (AllTvShowsScreenParam) -> Unit,
    onTvShowClick: (Int) -> Unit
) {
    topRatedTvShows?.let {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.top_rated),
                shouldShowSeeAllButton = true,
                onSeeAllClick = { onSeeAllClick(AllTvShowsScreenParam.TopRated) }
            ) {
                LazyRow {
                    items(
                        items = topRatedTvShows,
                        key = { it.id }
                    ) { tvShow ->
                        TvShowItem(
                            tvShow = tvShow,
                            onTvShowClick = onTvShowClick
                        )
                        Spacer(modifier = Modifier.width(SPACE_BETWEEN_MOVIES))
                    }
                    item {
                        SeeAllItem(onSeeAllClick = { onSeeAllClick(AllTvShowsScreenParam.TopRated) })
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
        Spacer(modifier = modifier.height(32.dp))
    }
}
