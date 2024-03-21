package uz.john.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.john.data.pagination.tv_shows.RecommendedTvShowsPagingSource
import uz.john.data.pagination.tv_shows.SimilarTvShowsPagingSource
import uz.john.data.pagination.tv_shows.TvShowsTrendingThisWeekPagingSource
import uz.john.data.remote.api.TvShowsApi
import uz.john.data.remote.model.tv_show.TvShowsResponseData
import uz.john.data.remote.model.tv_show.tv_show_details.TvShowDetailsData
import uz.john.util.ResultModel
import uz.john.util.invokeRequest
import java.util.Locale
import javax.inject.Inject

class TvShowsRepository @Inject constructor(
    private val tvShowsApi: TvShowsApi
) {
    private val region = Locale.getDefault().country
    private val language = Locale.getDefault().language

    suspend fun getTrendingThisWeekTvShows(page: Int): ResultModel<TvShowsResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            tvShowsApi.getTrendingThisWeekTvShows(
                language = language,
                page = page,
            )
        }
    }

    suspend fun getTvShowDetails(seriesId: Int): ResultModel<TvShowDetailsData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            tvShowsApi.getTvShowDetails(
                seriesId = seriesId,
                language = language,
            )
        }
    }

    suspend fun getSimilarTvShows(seriesId: Int, page: Int): ResultModel<TvShowsResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            tvShowsApi.getSimilarTvShows(
                seriesId = seriesId,
                language = language,
                page = page
            )
        }
    }

    suspend fun getRecommendedTvShows(seriesId: Int, page: Int): ResultModel<TvShowsResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            tvShowsApi.getRecommendedTvShows(
                seriesId = seriesId,
                language = language,
                page = page
            )
        }
    }

    fun getPaginatedRecommendedTvShows(seriesId: Int) = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            RecommendedTvShowsPagingSource(
                tvShowsApi = tvShowsApi,
                seriesId = seriesId,
                language = language
            )
        }
    ).flow

    fun getPaginatedSimilarTvShows(seriesId: Int) = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            SimilarTvShowsPagingSource(
                tvShowsApi = tvShowsApi,
                seriesId = seriesId,
                language = language
            )
        }
    ).flow

    fun getPaginatedTrendingThisWeekTvShows() = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            TvShowsTrendingThisWeekPagingSource(
                tvShowsApi = tvShowsApi,
                language = language
            )
        }
    ).flow
}