package uz.john.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.john.data.remote.FIRST_AIR_DATE_YEAR
import uz.john.data.remote.YEAR
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

    suspend fun searchTvShows(
        query: String,
        page: Int,
        includeAdult: Boolean = true,
        firstAirDateYear: Int? = null,
        year: String? = null
    ): ResultModel<TvShowsResponseData> = invokeRequest {
        val additionalParams = mutableMapOf<String, String>()
        firstAirDateYear?.let { additionalParams[FIRST_AIR_DATE_YEAR] = firstAirDateYear.toString() }
        year?.let { additionalParams[YEAR] = year }
        return@invokeRequest withContext(Dispatchers.IO) {
            tvShowsApi.searchTvShows(
                query = query,
                includeAdult = includeAdult,
                language = language,
                page = page,
                additionalParams = additionalParams
            )
        }
    }
}