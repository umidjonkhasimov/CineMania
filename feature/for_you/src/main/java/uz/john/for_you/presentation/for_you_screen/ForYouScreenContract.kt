package uz.john.for_you.presentation.for_you_screen

import uz.john.domain.model.movie.Movie
import uz.john.domain.model.tv_show.TvShow

sealed interface ForYouScreenContract {
    data class UiState(
        val isLoading: Boolean = true,
        val isLoggedIn: Boolean = false,
        val favoriteMovies: List<Movie>? = null,
        val watchLaterMovies: List<Movie>? = null,
        val favoriteTvShows: List<TvShow>? = null,
        val watchLaterTvShows: List<TvShow>? = null,
    ) : ForYouScreenContract

    sealed interface UiAction : ForYouScreenContract {
        data object GetSavedMovies : UiAction
    }

    sealed interface SideEffect : ForYouScreenContract {
        data class ShowErrorDialog(val errorMessage: String) : SideEffect
    }
}