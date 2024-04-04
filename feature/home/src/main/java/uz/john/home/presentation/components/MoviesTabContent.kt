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
import uz.john.domain.model.movie.Movie
import uz.john.home.R
import uz.john.home.presentation.home_screen.HomeScreenContract.UiState
import uz.john.paginated_movies_list.all_movies_screen.AllMoviesScreenParam
import uz.john.ui.components.LazyRowItemsHolder
import uz.john.ui.components.MovieCardItem
import uz.john.ui.components.MediaGenreItem
import uz.john.ui.components.SeeAllItem

private val SCREEN_PADDING = 16.dp
private val GENRES_HEIGHT = 120.dp
private val SPACE_BETWEEN_MOVIES = 8.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesTabContent(
    uiState: UiState,
    onMovieItemClick: (Int) -> Unit,
    onSeeAllClick: (AllMoviesScreenParam) -> Unit
) {
    val pagerState = rememberPagerState { uiState.nowPlayingMovies?.size ?: 0 }

    LazyColumn(
        modifier = Modifier.padding(top = SCREEN_PADDING)
    ) {
        trendingTodayMoviesPager(
            pagerState = pagerState,
            trendingTodayMovies = uiState.trendingTodayMovies,
            onMovieItemClick = { movieId ->
                onMovieItemClick(movieId)
            }
        )

        space()

        nowPlayingMovies(
            nowPlayingMovies = uiState.nowPlayingMovies,
            onSeeAllClick = onSeeAllClick,
            onMovieItemClick = { movieId ->
                onMovieItemClick(movieId)
            }
        )

        space()

        popularMovies(
            popularMovies = uiState.popularMovies,
            onSeeAllClick = onSeeAllClick,
            onMovieItemClick = onMovieItemClick
        )

        space()

        allGenres(
            allGenres = uiState.movieGenres,
            onGenreClick = onSeeAllClick
        )

        space()

        topRatedMovies(
            topRatedMovies = uiState.topRatedMovies,
            onSeeAllClick = onSeeAllClick,
            onMovieItemClick = onMovieItemClick
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.trendingTodayMoviesPager(
    pagerState: PagerState,
    trendingTodayMovies: List<Movie>?,
    onMovieItemClick: (Int) -> Unit
) {
    trendingTodayMovies?.let {
        item {
            MoviesCarouselItem(
                pagerState = pagerState,
                moviesList = trendingTodayMovies,
                title = stringResource(R.string.trending_today),
                modifier = Modifier,
                onItemClick = { movieId ->
                    onMovieItemClick(movieId)
                }
            )
        }
    }
}

private fun LazyListScope.nowPlayingMovies(
    nowPlayingMovies: List<Movie>?,
    onSeeAllClick: (AllMoviesScreenParam) -> Unit,
    onMovieItemClick: (Int) -> Unit
) {
    nowPlayingMovies?.let {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.now_playing),
                shouldShowSeeAllButton = true,
                onSeeAllClick = { onSeeAllClick(AllMoviesScreenParam.NowPlayingMovies) }
            ) {
                LazyRow {
                    items(
                        items = nowPlayingMovies,
                        key = { it.id }
                    ) { movieData ->
                        MovieCardItem(
                            movieData = movieData,
                            onMovieClick = { movieId ->
                                onMovieItemClick(movieId)
                            }
                        )
                        Spacer(modifier = Modifier.width(SPACE_BETWEEN_MOVIES))
                    }
                    item {
                        SeeAllItem(onSeeAllClick = { onSeeAllClick(AllMoviesScreenParam.NowPlayingMovies) })
                    }
                }
            }
        }
    }
}

private fun LazyListScope.popularMovies(
    popularMovies: List<Movie>?,
    onSeeAllClick: (AllMoviesScreenParam) -> Unit,
    onMovieItemClick: (Int) -> Unit
) {
    popularMovies?.let {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.popular_movies),
                shouldShowSeeAllButton = true,
                onSeeAllClick = { onSeeAllClick(AllMoviesScreenParam.PopularMovies) }
            ) {
                LazyRow {
                    items(
                        items = popularMovies,
                        key = { it.id }
                    ) { movieData ->
                        MovieCardItem(
                            movieData = movieData,
                            onMovieClick = { movieId ->
                                onMovieItemClick(movieId)
                            }
                        )
                        Spacer(modifier = Modifier.width(SPACE_BETWEEN_MOVIES))
                    }
                    item {
                        SeeAllItem(onSeeAllClick = { onSeeAllClick(AllMoviesScreenParam.PopularMovies) })
                    }
                }
            }
        }
    }
}

private fun LazyListScope.allGenres(
    allGenres: List<Genre>?,
    onGenreClick: (AllMoviesScreenParam) -> Unit
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
                                onGenreClick(AllMoviesScreenParam.AllMoviesByGenre(genreId = genreId, name = genre.name))
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}

private fun LazyListScope.topRatedMovies(
    topRatedMovies: List<Movie>?,
    onSeeAllClick: (AllMoviesScreenParam) -> Unit,
    onMovieItemClick: (Int) -> Unit
) {
    topRatedMovies?.let {
        item {
            LazyRowItemsHolder(
                modifier = Modifier.padding(start = SCREEN_PADDING),
                title = stringResource(R.string.top_rated),
                shouldShowSeeAllButton = true,
                onSeeAllClick = { onSeeAllClick(AllMoviesScreenParam.TopRated) }
            ) {
                LazyRow {
                    items(
                        items = topRatedMovies,
                        key = { it.id }
                    ) { movieData ->
                        MovieCardItem(
                            movieData = movieData,
                            onMovieClick = { movieId ->
                                onMovieItemClick(movieId)
                            }
                        )
                        Spacer(modifier = Modifier.width(SPACE_BETWEEN_MOVIES))
                    }
                    item {
                        SeeAllItem(onSeeAllClick = { onSeeAllClick(AllMoviesScreenParam.TopRated) })
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
