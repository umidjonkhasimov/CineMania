package uz.john.home.presentation.home_screen

import uz.john.domain.model.movie.Movie
import uz.john.domain.model.common.Genre
import uz.john.domain.model.tv_show.TvShow

sealed interface HomeScreenContract {
    data class UiState(
        val isLoading: Boolean = true,

        // Movies
        val trendingTodayMovies: List<Movie>? = null,
        val nowPlayingMovies: List<Movie>? = null,
        val popularMovies: List<Movie>? = null,
        val topRatedMovies: List<Movie>? = null,
        val movieGenres: List<Genre>? = null,

        // TvShows
        val trendingTodayTvShows: List<TvShow>? = null,
        val nowPlayingTvShows: List<TvShow>? = null,
        val popularTvShows: List<TvShow>? = null,
        val topRatedTvShows: List<TvShow>? = null,
        val tvShowGenres: List<Genre>? = null,
    ) : HomeScreenContract

    sealed interface UiAction : HomeScreenContract {
        data object InitializeMoviesScreen : UiAction
        data object InitializeTvShowsScreen : UiAction
    }

    sealed interface SideEffect : HomeScreenContract {
        data class ShowErrorDialog(val errorMessage: String) : SideEffect
    }
}