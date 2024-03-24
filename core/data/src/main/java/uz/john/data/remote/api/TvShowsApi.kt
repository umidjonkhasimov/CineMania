package uz.john.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import uz.john.data.remote.APPEND_CREDITS
import uz.john.data.remote.APPEND_IMAGES
import uz.john.data.remote.APPEND_TO_RESPONSE
import uz.john.data.remote.APPEND_VIDEOS
import uz.john.data.remote.INCLUDE_ADULT
import uz.john.data.remote.LANGUAGE
import uz.john.data.remote.PAGE
import uz.john.data.remote.QUERY
import uz.john.data.remote.RECOMMENDED_TV_SHOWS_ENDPOINT
import uz.john.data.remote.SEARCH_TV_SHOWS_ENDPOINT
import uz.john.data.remote.SERIES_ID
import uz.john.data.remote.SIMILAR_TV_SHOWS_ENDPOINT
import uz.john.data.remote.TRENDING_TIME_WINDOW
import uz.john.data.remote.TRENDING_TIME_WINDOW_WEEK
import uz.john.data.remote.TRENDING_TV_SHOWS_ENDPOINT
import uz.john.data.remote.TV_SHOW_DETAILS_ENDPOINT
import uz.john.data.remote.model.tv_show.TvShowsResponseData
import uz.john.data.remote.model.tv_show.tv_show_details.TvShowDetailsData

interface TvShowsApi {
    @GET(TRENDING_TV_SHOWS_ENDPOINT)
    suspend fun getTrendingThisWeekTvShows(
        @Path(TRENDING_TIME_WINDOW) timeWindow: String = TRENDING_TIME_WINDOW_WEEK,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
    ): Response<TvShowsResponseData>

    @GET(TV_SHOW_DETAILS_ENDPOINT)
    suspend fun getTvShowDetails(
        @Path(SERIES_ID) seriesId: Int,
        @Query(LANGUAGE) language: String,
        @Query(APPEND_TO_RESPONSE) appendToResponse: String = "$APPEND_IMAGES,$APPEND_VIDEOS,$APPEND_CREDITS"
    ): Response<TvShowDetailsData>

    @GET(SIMILAR_TV_SHOWS_ENDPOINT)
    suspend fun getSimilarTvShows(
        @Path(SERIES_ID) seriesId: Int,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
    ): Response<TvShowsResponseData>

    @GET(RECOMMENDED_TV_SHOWS_ENDPOINT)
    suspend fun getRecommendedTvShows(
        @Path(SERIES_ID) seriesId: Int,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
    ): Response<TvShowsResponseData>

    @GET(SEARCH_TV_SHOWS_ENDPOINT)
    suspend fun searchTvShows(
        @Query(QUERY) query: String,
        @Query(INCLUDE_ADULT) includeAdult: Boolean,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
        @QueryMap additionalParams: Map<String, String>,
    ): Response<TvShowsResponseData>
}