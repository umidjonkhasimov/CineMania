package uz.john.home.presentation.home_screen

import uz.john.domain.model.movie.Movie
import uz.john.domain.model.common.Genre

sealed interface HomeScreenContract {
    data class UiState(
        val isLoading: Boolean = true,
        val trendingTodayMovies: List<Movie>? = null,
        val nowPlayingMovies: List<Movie>? = null,
        val popularMovies: List<Movie>? = null,
        val topRated: List<Movie>? = null,
        val genres: List<Genre>? = null,
    ) : HomeScreenContract

    sealed interface UiAction : HomeScreenContract {
        data object InitializeScreen : UiAction
    }

    sealed interface SideEffect : HomeScreenContract {
        data class ShowErrorDialog(val errorMessage: String) : SideEffect
    }
}