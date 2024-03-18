package uz.john.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.john.data.remote.api.TvShowsApi
import uz.john.data.remote.model.tv_show.TvShowsResponseData
import uz.john.util.ResultModel
import uz.john.util.invokeRequest
import java.util.Locale
import javax.inject.Inject

class TvShowsRepository @Inject constructor(
    private val tvShowsApi: TvShowsApi
) {
    private val region = Locale.getDefault().country
    private val language = Locale.getDefault().language

    suspend fun getTrendingThisWeekMovies(page: Int): ResultModel<TvShowsResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            tvShowsApi.getTrendingThisWeekTvShows(
                language = language,
                page = page,
            )
        }
    }
}