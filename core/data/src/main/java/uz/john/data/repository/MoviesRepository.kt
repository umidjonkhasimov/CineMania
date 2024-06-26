package uz.john.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import uz.john.data.remote.INCLUDE_ADULT
import uz.john.data.remote.INCLUDE_VIDEO
import uz.john.data.remote.MOVIE_MEDIA_TYPE
import uz.john.data.remote.PRIMARY_RELEASE_DATE_GTE
import uz.john.data.remote.PRIMARY_RELEASE_DATE_LTE
import uz.john.data.remote.PRIMARY_RELEASE_YEAR
import uz.john.data.remote.SORT_BY
import uz.john.data.remote.VOTE_AVERAGE_GTE
import uz.john.data.remote.VOTE_AVERAGE_LTE
import uz.john.data.remote.VOTE_COUNT_GTE
import uz.john.data.remote.VOTE_COUNT_LTE
import uz.john.data.remote.WITH_CAST
import uz.john.data.remote.WITH_COMPANIES
import uz.john.data.remote.WITH_CREW
import uz.john.data.remote.WITH_GENRES
import uz.john.data.remote.WITH_PEOPLE
import uz.john.data.remote.YEAR
import uz.john.data.remote.api.MoviesApi
import uz.john.data.remote.model.movie.AccountStateOfMediaData
import uz.john.data.remote.model.movie.GenresResponseData
import uz.john.data.remote.model.movie.MoviesResponseData
import uz.john.data.remote.model.movie.movie_details.MovieDetailsData
import uz.john.data.remote.model.movie.post.AddToFavoriteRequest
import uz.john.data.remote.model.movie.post.AddToWatchLaterRequest
import uz.john.util.ApiResponse
import uz.john.util.ResultModel
import uz.john.util.invokeRequest
import java.util.Locale
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val dataStoreRepository: DataStoreRepository
) {
    private val region = Locale.getDefault().country
    private val language = Locale.getDefault().language

    suspend fun getTrendingTodayMovies(page: Int): ResultModel<MoviesResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            moviesApi.getTrendingTodayMovies(
                language = language,
                page = page,
            )
        }
    }

    suspend fun getTopRatedMovies(page: Int): ResultModel<MoviesResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            moviesApi.getTopRatedMovies(
                language = language,
                page = page,
                region = region
            )
        }
    }

    suspend fun getRecommendedMovies(movieId: Int, page: Int): ResultModel<MoviesResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            moviesApi.getRecommendedMovies(
                movieId = movieId,
                language = language,
                page = page,
            )
        }
    }

    suspend fun getMovieDetails(movieId: Int): ResultModel<MovieDetailsData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            moviesApi.getMovieDetails(
                movieId = movieId,
                language = language
            )
        }
    }

    suspend fun getSimilarMovies(movieId: Int, page: Int): ResultModel<MoviesResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            moviesApi.getSimilarMovies(
                movieId = movieId,
                language = language,
                page = page
            )
        }
    }

    suspend fun getAllGenres(): ResultModel<GenresResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            moviesApi.getAllGenres(
                language = language,
            )
        }
    }

    suspend fun getTrendingThisWeekMovies(page: Int): ResultModel<MoviesResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            moviesApi.getTrendingThisWeekMovies(
                language = language,
                page = page,
            )
        }
    }

    suspend fun searchMovies(
        query: String,
        page: Int,
        primaryReleaseYear: String? = null,
        year: String? = null
    ): ResultModel<MoviesResponseData> = invokeRequest {
        val additionalQueries = mutableMapOf<String, String>()
        primaryReleaseYear?.let { additionalQueries[PRIMARY_RELEASE_YEAR] = primaryReleaseYear }
        year?.let { additionalQueries[YEAR] = year }
        val includeAdult = dataStoreRepository.userData.first().includeAdult

        return@invokeRequest withContext(Dispatchers.IO) {
            moviesApi.searchMovies(
                query = query,
                page = page,
                includeAdult = includeAdult,
                language = language,
                region = region,
                additionalParams = additionalQueries,
            )
        }
    }

    suspend fun discoverMovies(
        page: Int,
        includeVideo: Boolean? = null,
        primaryReleaseYear: String? = null,
        primaryReleaseDateGte: String? = null,
        primaryReleaseDateLte: String? = null,
        sortBy: String? = null,
        voteAverageGte: Float? = null,
        voteAverageLte: Float? = null,
        voteCountGte: Float? = null,
        voteCountLte: Float? = null,
        withCast: String? = null,
        withCrew: String? = null,
        withCompanies: String? = null,
        withGenres: String? = null,
        withPeople: String? = null,
    ): ResultModel<MoviesResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            val queryParams = mutableMapOf<String, String>()
            queryParams[INCLUDE_ADULT] = dataStoreRepository.userData.first().includeAdult.toString()

            includeVideo?.let { queryParams[INCLUDE_VIDEO] = includeVideo.toString() }
            primaryReleaseYear?.let { queryParams[PRIMARY_RELEASE_YEAR] = primaryReleaseYear.toString() }
            primaryReleaseDateGte?.let { queryParams[PRIMARY_RELEASE_DATE_GTE] = primaryReleaseDateGte.toString() }
            primaryReleaseDateLte?.let { queryParams[PRIMARY_RELEASE_DATE_LTE] = primaryReleaseDateLte.toString() }
            sortBy?.let { queryParams[SORT_BY] = sortBy.toString() }
            voteAverageGte?.let { queryParams[VOTE_AVERAGE_GTE] = voteAverageGte.toString() }
            voteAverageLte?.let { queryParams[VOTE_AVERAGE_LTE] = voteAverageLte.toString() }
            voteCountGte?.let { queryParams[VOTE_COUNT_GTE] = voteCountGte.toString() }
            voteCountLte?.let { queryParams[VOTE_COUNT_LTE] = voteCountLte.toString() }
            withCast?.let { queryParams[WITH_CAST] = withCast.toString() }
            withCrew?.let { queryParams[WITH_CREW] = withCrew.toString() }
            withCompanies?.let { queryParams[WITH_COMPANIES] = withCompanies.toString() }
            withGenres?.let { queryParams[WITH_GENRES] = withGenres.toString() }
            withPeople?.let { queryParams[WITH_PEOPLE] = withPeople.toString() }
            moviesApi.discoverMovies(
                page = page,
                language = language,
                region = region,
                queryParams = queryParams,
            )
        }
    }

    suspend fun getAccountStatesOfMovie(movieId: Int): ResultModel<AccountStateOfMediaData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            val sessionId = dataStoreRepository.userData.first().sessionId

            moviesApi.getAccountStateOfMovie(movieId = movieId, sessionId = sessionId)
        }
    }

    suspend fun getFavoriteMovies(
        page: Int,
        accountId: Int = 1
    ): ResultModel<MoviesResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            val sessionId = dataStoreRepository.userData.first().sessionId

            moviesApi.getFavoriteMovies(
                accountId = accountId,
                page = page,
                sessionId = sessionId,
                language = language
            )
        }
    }

    suspend fun getWatchLaterMovies(
        page: Int,
        accountId: Int = 1
    ): ResultModel<MoviesResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            val sessionId = dataStoreRepository.userData.first().sessionId

            moviesApi.getWatchLaterMovies(
                accountId = accountId,
                page = page,
                sessionId = sessionId,
                language = language
            )
        }
    }

    suspend fun setMovieFavorite(movieId: Int, setFavorite: Boolean): ResultModel<ApiResponse> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            val sessionId = dataStoreRepository.userData.first().sessionId

            moviesApi.setMovieFavorite(
                sessionId = sessionId,
                addToFavoriteRequest = AddToFavoriteRequest(
                    mediaType = MOVIE_MEDIA_TYPE,
                    mediaId = movieId,
                    favorite = setFavorite
                )
            )
        }
    }

    suspend fun setMovieWatchLater(movieId: Int, setWatchLater: Boolean): ResultModel<ApiResponse> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            val sessionId = dataStoreRepository.userData.first().sessionId

            moviesApi.setMovieWatchLater(
                sessionId = sessionId,
                addToWatchLaterRequest = AddToWatchLaterRequest(
                    mediaType = MOVIE_MEDIA_TYPE,
                    mediaId = movieId,
                    watchlist = setWatchLater
                )
            )
        }
    }
}