package uz.john.cinemania.movie_details_screen

import uz.john.domain.model.Movie
import uz.john.domain.model.movie_details.MovieDetails

sealed interface MovieDetailsScreenContract {
    data class UiState(
        val isLoading: Boolean = true,
        val movieDetails: MovieDetails? = null,
        val similarMovies: List<Movie> = emptyList()
    ) : MovieDetailsScreenContract

    sealed interface SideEffect : MovieDetailsScreenContract {
        data class ShowErrorDialog(val message: String) : SideEffect
    }

    sealed interface UiAction : MovieDetailsScreenContract {
        data object GetMovieDetails : UiAction
    }
}