package uz.john.home.presentation.home_screen

import uz.john.domain.model.Movie

sealed interface HomeScreenContract {
    data class UiState(
        val isLoading: Boolean = true,
        val nowPlayingMovies: List<Movie>? = null,
        val popularMovies: List<Movie>? = null,
        val topRated: List<Movie>? = null,
        val categories: List<Movie>? = null,
        val trendingToday: List<Movie>? = null
    ) : HomeScreenContract

    sealed interface UiAction : HomeScreenContract {
        data object InitializeScreen : UiAction
    }

    sealed interface SideEffect : HomeScreenContract {
        data class ShowErrorDialog(val errorMessage: String) : SideEffect
    }
}