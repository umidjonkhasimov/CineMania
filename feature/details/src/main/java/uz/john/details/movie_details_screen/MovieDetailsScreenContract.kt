package uz.john.details.movie_details_screen

import uz.john.domain.model.movie.Movie
import uz.john.domain.model.movie.movie_details.MovieDetails

sealed interface MovieDetailsScreenContract {
    data class UiState(
        val isLoading: Boolean = true,
        val movieDetails: MovieDetails? = null,
        val similarMovies: List<Movie> = emptyList(),
        val recommendedMovies: List<Movie> = emptyList()
    ) : MovieDetailsScreenContract

    sealed interface SideEffect : MovieDetailsScreenContract {
        data class ShowErrorDialog(val message: String) : SideEffect
    }

    sealed interface UiAction : MovieDetailsScreenContract {
        data object GetMovieDetails : UiAction
    }
}