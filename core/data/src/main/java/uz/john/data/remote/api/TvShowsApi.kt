package uz.john.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.john.data.remote.LANGUAGE
import uz.john.data.remote.PAGE
import uz.john.data.remote.TRENDING_TIME_WINDOW
import uz.john.data.remote.TRENDING_TIME_WINDOW_WEEK
import uz.john.data.remote.model.tv_show.TvShowsResponseData

interface TvShowsApi {
    @GET("trending/tv/{time_window}")
    suspend fun getTrendingThisWeekTvShows(
        @Path(TRENDING_TIME_WINDOW) timeWindow: String = TRENDING_TIME_WINDOW_WEEK,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
    ): Response<TvShowsResponseData>
}