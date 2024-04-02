package uz.john.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import uz.john.data.remote.MOVIE_ACCOUNT_STATE_ENDPOINT
import uz.john.data.remote.ADD_TO_FAVORITE_ENDPOINT
import uz.john.data.remote.ADD_TO_WATCH_LIST_ENDPOINT
import uz.john.data.remote.ALL_GENRES_ENDPOINT
import uz.john.data.remote.APPEND_CREDITS
import uz.john.data.remote.APPEND_IMAGES
import uz.john.data.remote.APPEND_TO_RESPONSE
import uz.john.data.remote.APPEND_VIDEOS
import uz.john.data.remote.DISCOVER_MOVIES
import uz.john.data.remote.FAVORITE_MOVIES_ENDPOINT
import uz.john.data.remote.INCLUDE_ADULT
import uz.john.data.remote.LANGUAGE
import uz.john.data.remote.MOVIE_DETAILS_ENDPOINT
import uz.john.data.remote.MOVIE_ID
import uz.john.data.remote.PAGE
import uz.john.data.remote.QUERY
import uz.john.data.remote.RECOMMENDED_MOVIES_ENDPOINT
import uz.john.data.remote.REGION
import uz.john.data.remote.SEARCH_MOVIE_ENDPOINT
import uz.john.data.remote.SIMILAR_MOVIES_ENDPOINT
import uz.john.data.remote.SORT_BY
import uz.john.data.remote.TOP_RATED_MOVIES_ENDPOINT
import uz.john.data.remote.TRENDING_ENDPOINT
import uz.john.data.remote.TRENDING_TIME_WINDOW
import uz.john.data.remote.TRENDING_TIME_WINDOW_DAY
import uz.john.data.remote.TRENDING_TIME_WINDOW_WEEK
import uz.john.data.remote.WATCH_LATER_MOVIES_ENDPOINT
import uz.john.data.remote.model.movie.AccountStateOfMediaData
import uz.john.data.remote.model.movie.GenresResponseData
import uz.john.data.remote.model.movie.MoviesResponseData
import uz.john.data.remote.model.movie.movie_details.MovieDetailsData
import uz.john.data.remote.model.movie.post.AddToFavoriteRequest
import uz.john.data.remote.model.movie.post.AddToWatchLaterRequest
import uz.john.util.ApiResponse

interface MoviesApi {
    @GET(TRENDING_ENDPOINT)
    suspend fun getTrendingTodayMovies(
        @Path(TRENDING_TIME_WINDOW) timeWindow: String = TRENDING_TIME_WINDOW_DAY,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
    ): Response<MoviesResponseData>

    @GET(TRENDING_ENDPOINT)
    suspend fun getTrendingThisWeekMovies(
        @Path(TRENDING_TIME_WINDOW) timeWindow: String = TRENDING_TIME_WINDOW_WEEK,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
    ): Response<MoviesResponseData>

    @GET(TOP_RATED_MOVIES_ENDPOINT)
    suspend fun getTopRatedMovies(
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
        @Query(REGION) region: String,
    ): Response<MoviesResponseData>

    @GET(RECOMMENDED_MOVIES_ENDPOINT)
    suspend fun getRecommendedMovies(
        @Path(MOVIE_ID) movieId: Int,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
    ): Response<MoviesResponseData>

    @GET(MOVIE_DETAILS_ENDPOINT)
    suspend fun getMovieDetails(
        @Path(MOVIE_ID) movieId: Int,
        @Query(LANGUAGE) language: String,
        @Query(APPEND_TO_RESPONSE) appendToResponse: String = "$APPEND_IMAGES,$APPEND_CREDITS,$APPEND_VIDEOS"
    ): Response<MovieDetailsData>

    @GET(SIMILAR_MOVIES_ENDPOINT)
    suspend fun getSimilarMovies(
        @Path(MOVIE_ID) movieId: Int,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
    ): Response<MoviesResponseData>

    @GET(DISCOVER_MOVIES)
    suspend fun discoverMovies(
        @Query(PAGE) page: Int,
        @Query(LANGUAGE) language: String,
        @Query(REGION) region: String,
        @QueryMap queryParams: Map<String, String>,
    ): Response<MoviesResponseData>

    @GET(ALL_GENRES_ENDPOINT)
    suspend fun getAllGenres(
        @Query(LANGUAGE) language: String,
    ): Response<GenresResponseData>

    @GET(SEARCH_MOVIE_ENDPOINT)
    suspend fun searchMovies(
        @Query(QUERY) query: String,
        @Query(INCLUDE_ADULT) includeAdult: Boolean,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
        @Query(REGION) region: String,
        @QueryMap additionalParams: Map<String, String>,
    ): Response<MoviesResponseData>

    @GET(MOVIE_ACCOUNT_STATE_ENDPOINT)
    suspend fun getAccountStateOfMovie(
        @Path(MOVIE_ID) movieId: Int,
        @Query("session_id") sessionId: String
    ): Response<AccountStateOfMediaData>

    @GET(FAVORITE_MOVIES_ENDPOINT)
    suspend fun getFavoriteMovies(
        @Path("account_id") accountId: Int,
        @Query(PAGE) page: Int,
        @Query("session_id") sessionId: String,
        @Query(LANGUAGE) language: String,
        @Query(SORT_BY) sortBy: String = "created_at.asc"
    ): Response<MoviesResponseData>

    @GET(WATCH_LATER_MOVIES_ENDPOINT)
    suspend fun getWatchLaterMovies(
        @Path("account_id") accountId: Int,
        @Query(PAGE) page: Int,
        @Query("session_id") sessionId: String,
        @Query(LANGUAGE) language: String,
        @Query(SORT_BY) sortBy: String = "created_at.asc"
    ): Response<MoviesResponseData>

    @POST(ADD_TO_FAVORITE_ENDPOINT)
    suspend fun setMovieFavorite(
        @Query("session_id") sessionId: String,
        @Body addToFavoriteRequest: AddToFavoriteRequest
    ): Response<ApiResponse>

    @POST(ADD_TO_WATCH_LIST_ENDPOINT)
    suspend fun setMovieWatchLater(
        @Query("session_id") sessionId: String,
        @Body addToWatchLaterRequest: AddToWatchLaterRequest
    ): Response<ApiResponse>
}