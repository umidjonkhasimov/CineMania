package uz.john.paginated_movies_list.all_tv_shows_screen

import android.content.Context
import kotlinx.serialization.Serializable
import uz.john.paginated_movies_list.R

@Serializable
sealed class AllTvShowsScreenParam {
    abstract fun getTitle(context: Context): String

    @Serializable
    data class SimilarTvShows(val seriesId: Int) : AllTvShowsScreenParam() {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.similar_tv_shows)
        }
    }

    @Serializable
    data class RecommendedTvShows(val seriesId: Int) : AllTvShowsScreenParam() {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.recommended_tv_shows)
        }
    }

    @Serializable
    data object TvShowsTrendingThisWeek : AllTvShowsScreenParam() {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.trending_this_week)
        }
    }

    @Serializable
    data class TvShowsBySearchResult(val query: String) : AllTvShowsScreenParam() {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.search_for, query)
        }
    }
}