package uz.john.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import uz.john.data.remote.ADD_TO_RESPONSE
import uz.john.data.remote.ALL_GENRES_ENDPOINT
import uz.john.data.remote.APPEND_CREDITS
import uz.john.data.remote.APPEND_IMAGES
import uz.john.data.remote.APPEND_VIDEOS
import uz.john.data.remote.DISCOVER_MOVIES
import uz.john.data.remote.LANGUAGE
import uz.john.data.remote.MOVIE_DETAILS_ENDPOINT
import uz.john.data.remote.MOVIE_ID
import uz.john.data.remote.PAGE
import uz.john.data.remote.RECOMMENDED_MOVIES_ENDPOINT
import uz.john.data.remote.REGION
import uz.john.data.remote.SIMILAR_MOVIES_ENDPOINT
import uz.john.data.remote.TOP_RATED_MOVIES_ENDPOINT
import uz.john.data.remote.TRENDING_ENDPOINT
import uz.john.data.remote.TRENDING_TIME_WINDOW
import uz.john.data.remote.TRENDING_TIME_WINDOW_DAY
import uz.john.data.remote.TRENDING_TIME_WINDOW_WEEK
import uz.john.data.remote.model.movie.GenresResponseData
import uz.john.data.remote.model.movie.MoviesResponseData
import uz.john.data.remote.model.movie.movie_details.MovieDetailsData

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
        @Query(ADD_TO_RESPONSE) appendToResponse: String = "$APPEND_IMAGES,$APPEND_CREDITS,$APPEND_VIDEOS"
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
}