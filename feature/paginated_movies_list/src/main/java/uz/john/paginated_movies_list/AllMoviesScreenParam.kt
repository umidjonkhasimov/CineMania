package uz.john.paginated_movies_list

import androidx.annotation.StringRes
import kotlinx.serialization.Serializable

@Serializable
sealed class AllMoviesScreenParam(
    @StringRes
    val title: Int
) {
    @Serializable
    data object NowPlayingMovies : AllMoviesScreenParam(R.string.now_playing)

    @Serializable
    data object PopularMovies : AllMoviesScreenParam(R.string.popular_movies)

    @Serializable
    data object TopRated : AllMoviesScreenParam(R.string.top_rated)

    @Serializable
    data class SimilarMovies(val movieId: Int) : AllMoviesScreenParam(R.string.similar_movies)

    @Serializable
    data class RecommendedMovies(val movieId: Int) : AllMoviesScreenParam(R.string.recommended)

    @Serializable
    data class AllMoviesByGenre(val genreId: Int, val name: String) : AllMoviesScreenParam(R.string.genre)
}