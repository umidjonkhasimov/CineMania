package uz.john.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import uz.john.data.remote.ACCOUNT_ID_PATH
import uz.john.data.remote.ADD_TO_FAVORITE_ENDPOINT
import uz.john.data.remote.ADD_TO_WATCH_LIST_ENDPOINT
import uz.john.data.remote.ALL_TV_SHOW_GENRES_ENDPOINT
import uz.john.data.remote.APPEND_CREDITS
import uz.john.data.remote.APPEND_IMAGES
import uz.john.data.remote.APPEND_TO_RESPONSE
import uz.john.data.remote.APPEND_VIDEOS
import uz.john.data.remote.DISCOVER_TV_SHOWS
import uz.john.data.remote.FAVORITE_TV_SHOWS_ENDPOINT
import uz.john.data.remote.INCLUDE_ADULT
import uz.john.data.remote.LANGUAGE
import uz.john.data.remote.PAGE
import uz.john.data.remote.QUERY
import uz.john.data.remote.RECOMMENDED_TV_SHOWS_ENDPOINT
import uz.john.data.remote.SEARCH_TV_SHOWS_ENDPOINT
import uz.john.data.remote.SERIES_ID
import uz.john.data.remote.SESSION_ID_PATH
import uz.john.data.remote.SIMILAR_TV_SHOWS_ENDPOINT
import uz.john.data.remote.SORT_BY
import uz.john.data.remote.TOP_RATED_TV_SHOWS_ENDPOINT
import uz.john.data.remote.TRENDING_TIME_WINDOW
import uz.john.data.remote.TRENDING_TIME_WINDOW_DAY
import uz.john.data.remote.TRENDING_TIME_WINDOW_WEEK
import uz.john.data.remote.TRENDING_TV_SHOWS_ENDPOINT
import uz.john.data.remote.TV_SHOW_ACCOUNT_STATE_ENDPOINT
import uz.john.data.remote.TV_SHOW_DETAILS_ENDPOINT
import uz.john.data.remote.TV_SHOW_ID
import uz.john.data.remote.WATCH_LATER_TV_SHOWS_ENDPOINT
import uz.john.data.remote.model.movie.AccountStateOfMediaData
import uz.john.data.remote.model.movie.GenresResponseData
import uz.john.data.remote.model.movie.post.AddToFavoriteRequest
import uz.john.data.remote.model.movie.post.AddToWatchLaterRequest
import uz.john.data.remote.model.tv_show.TvShowsResponseData
import uz.john.data.remote.model.tv_show.tv_show_details.TvShowDetailsData
import uz.john.util.ApiResponse

interface TvShowsApi {
    @GET(TRENDING_TV_SHOWS_ENDPOINT)
    suspend fun getTrendingTodayTvShows(
        @Path(TRENDING_TIME_WINDOW) timeWindow: String = TRENDING_TIME_WINDOW_DAY,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
    ): Response<TvShowsResponseData>

    @GET(TRENDING_TV_SHOWS_ENDPOINT)
    suspend fun getTrendingThisWeekTvShows(
        @Path(TRENDING_TIME_WINDOW) timeWindow: String = TRENDING_TIME_WINDOW_WEEK,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
    ): Response<TvShowsResponseData>

    @GET(TOP_RATED_TV_SHOWS_ENDPOINT)
    suspend fun getTopRatedTvShows(
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int
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

    @GET(FAVORITE_TV_SHOWS_ENDPOINT)
    suspend fun getFavoriteTvShows(
        @Path(ACCOUNT_ID_PATH) accountId: Int,
        @Query(PAGE) page: Int,
        @Query(SESSION_ID_PATH) sessionId: String,
        @Query(LANGUAGE) language: String,
        @Query(SORT_BY) sortBy: String = "created_at.asc"
    ): Response<TvShowsResponseData>

    @GET(WATCH_LATER_TV_SHOWS_ENDPOINT)
    suspend fun getWatchLaterTvShows(
        @Path(ACCOUNT_ID_PATH) accountId: Int,
        @Query(PAGE) page: Int,
        @Query(SESSION_ID_PATH) sessionId: String,
        @Query(LANGUAGE) language: String,
        @Query(SORT_BY) sortBy: String = "created_at.asc"
    ): Response<TvShowsResponseData>

    @POST(ADD_TO_FAVORITE_ENDPOINT)
    suspend fun setTvShowFavorite(
        @Query(SESSION_ID_PATH) sessionId: String,
        @Body addToFavoriteRequest: AddToFavoriteRequest
    ): Response<ApiResponse>

    @POST(ADD_TO_WATCH_LIST_ENDPOINT)
    suspend fun setTvShowWatchLater(
        @Query(SESSION_ID_PATH) sessionId: String,
        @Body addToWatchLaterRequest: AddToWatchLaterRequest
    ): Response<ApiResponse>

    @GET(TV_SHOW_ACCOUNT_STATE_ENDPOINT)
    suspend fun getAccountStateOfTvShow(
        @Path(TV_SHOW_ID) tvShowId: Int,
        @Query(SESSION_ID_PATH) sessionId: String
    ): Response<AccountStateOfMediaData>

    @GET(ALL_TV_SHOW_GENRES_ENDPOINT)
    suspend fun getAllGenres(
        @Query(LANGUAGE) language: String,
    ): Response<GenresResponseData>

    @GET(DISCOVER_TV_SHOWS)
    suspend fun discoverTvShows(
        @Query(PAGE) page: Int,
        @Query(LANGUAGE) language: String,
        @QueryMap queryParams: Map<String, String>,
    ): Response<TvShowsResponseData>
}