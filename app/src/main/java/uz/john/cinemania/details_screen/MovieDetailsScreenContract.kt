package uz.john.cinemania.details_screen

import uz.john.domain.model.movie_details.MovieDetails

sealed interface MovieDetailsScreenContract {
    data class UiState(
        val isLoading: Boolean = true,
        val movieDetails: MovieDetails? = null
    ) : MovieDetailsScreenContract

    sealed interface SideEffect : MovieDetailsScreenContract {
        data class ShowErrorDialog(val message: String) : SideEffect
    }

    sealed interface UiAction : MovieDetailsScreenContract {
        data class GetMovieDetails(val movieId: Int) : UiAction
    }
}