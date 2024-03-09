package uz.john.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.john.data.remote.ADD_TO_RESPONSE
import uz.john.data.remote.APPEND_CREDITS
import uz.john.data.remote.APPEND_IMAGES
import uz.john.data.remote.APPEND_VIDEOS
import uz.john.data.remote.LANGUAGE
import uz.john.data.remote.MOVIE_DETAILS_ENDPOINT
import uz.john.data.remote.MOVIE_ID
import uz.john.data.remote.NOW_PLAYING_ENDPOINT
import uz.john.data.remote.PAGE
import uz.john.data.remote.POPULAR_MOVIES_ENDPOINT
import uz.john.data.remote.REGION
import uz.john.data.remote.SIMILAR_MOVIES_ENDPOINT
import uz.john.data.remote.TOP_RATED_MOVIES_ENDPOINT
import uz.john.data.remote.model.details.MovieDetailsData
import uz.john.data.remote.model.home.MoviesResponseData

interface MoviesApi {
    @GET(NOW_PLAYING_ENDPOINT)
    suspend fun getNowPlayingMovies(
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
        @Query(REGION) region: String,
    ): Response<MoviesResponseData>

    @GET(POPULAR_MOVIES_ENDPOINT)
    suspend fun getPopularMovies(
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
        @Query(REGION) region: String,
    ): Response<MoviesResponseData>

    @GET(TOP_RATED_MOVIES_ENDPOINT)
    suspend fun getTopRatedMovies(
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
        @Query(REGION) region: String,
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
}