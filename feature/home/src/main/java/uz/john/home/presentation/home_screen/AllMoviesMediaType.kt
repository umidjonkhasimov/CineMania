package uz.john.home.presentation.home_screen

import androidx.annotation.StringRes
import kotlinx.serialization.Serializable
import uz.john.home.R

@Serializable
sealed class AllMoviesMediaType(
    @StringRes
    val title: Int
) {
    @Serializable
    data object PopularMovies : AllMoviesMediaType(R.string.popular_movies)

    @Serializable
    data object TopRated : AllMoviesMediaType(R.string.top_rated)

    @Serializable
    data class SimilarMovies(val movieId: Int) : AllMoviesMediaType(R.string.similar_movies)
}