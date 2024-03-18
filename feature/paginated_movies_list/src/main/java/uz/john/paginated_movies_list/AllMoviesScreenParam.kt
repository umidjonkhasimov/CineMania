package uz.john.paginated_movies_list

import android.content.Context
import kotlinx.serialization.Serializable

@Serializable
sealed class AllMoviesScreenParam {
    abstract fun getTitle(context: Context): String

    @Serializable
    data object NowPlayingMovies : AllMoviesScreenParam() {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.now_playing)
        }
    }

    @Serializable
    data object PopularMovies : AllMoviesScreenParam() {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.popular_movies)
        }
    }

    @Serializable
    data object TopRated : AllMoviesScreenParam() {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.top_rated)
        }
    }

    @Serializable
    data class SimilarMovies(val movieId: Int) : AllMoviesScreenParam() {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.similar_movies)
        }
    }

    @Serializable
    data class RecommendedMovies(val movieId: Int) : AllMoviesScreenParam() {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.recommended)
        }
    }

    @Serializable
    data class AllMoviesByGenre(val genreId: Int, val name: String) : AllMoviesScreenParam() {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.genre, name)
        }
    }

    @Serializable
    data object MoviesTrendingThisWeek : AllMoviesScreenParam() {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.trending_this_week)
        }
    }

    @Serializable
    data class AllMoviesBySearchQuery(val query: String) : AllMoviesScreenParam() {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.search_for, query)
        }
    }
}