package uz.john.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.john.data.pagination.PopularMoviesPagingSource
import uz.john.data.pagination.SimilarMoviesPagingSource
import uz.john.data.pagination.TopRatedMoviesPagingSource
import uz.john.data.remote.api.MoviesApi
import uz.john.data.remote.model.details.MovieDetailsData
import uz.john.data.remote.model.home.MoviesResponseData
import uz.john.util.ResultModel
import uz.john.util.invokeRequest
import java.util.Locale
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi,
) {
    private val region = Locale.getDefault().country
    private val language = Locale.getDefault().language

    suspend fun getNowPlayingMovies(page: Int): ResultModel<MoviesResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            moviesApi.getNowPlayingMovies(
                language = language,
                page = page,
                region = region
            )
        }
    }

    suspend fun getPopularMovies(page: Int): ResultModel<MoviesResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            moviesApi.getPopularMovies(
                language = language,
                page = page,
                region = region
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

    fun getPaginatedPopularMovies() = Pager(
        config = PagingConfig(
            pageSize = 20
        ),
        pagingSourceFactory = {
            PopularMoviesPagingSource(
                moviesApi = moviesApi,
                language = language,
                region = region
            )
        }
    ).flow

    fun getPaginatedTopRatedMovies() = Pager(
        config = PagingConfig(
            pageSize = 20
        ),
        pagingSourceFactory = {
            TopRatedMoviesPagingSource(
                moviesApi = moviesApi,
                language = language,
                region = region
            )
        }
    ).flow

    fun getPaginatedSimilarMovies(movieId: Int) = Pager(
        config = PagingConfig(
            pageSize = 20
        ),
        pagingSourceFactory = {
            SimilarMoviesPagingSource(
                moviesApi = moviesApi,
                movieId = movieId,
                language = language
            )
        }
    ).flow
}