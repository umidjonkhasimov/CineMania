package uz.john.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import uz.john.data.remote.AIR_DATE_GTE
import uz.john.data.remote.AIR_DATE_LTE
import uz.john.data.remote.FIRST_AIR_DATE_GTE
import uz.john.data.remote.FIRST_AIR_DATE_LTE
import uz.john.data.remote.FIRST_AIR_DATE_YEAR
import uz.john.data.remote.INCLUDE_ADULT
import uz.john.data.remote.SCREENED_THEATRICALLY
import uz.john.data.remote.SORT_BY
import uz.john.data.remote.TIMEZONE
import uz.john.data.remote.TV_SHOW_MEDIA_TYPE
import uz.john.data.remote.VOTE_AVERAGE_GTE
import uz.john.data.remote.VOTE_AVERAGE_LTE
import uz.john.data.remote.VOTE_COUNT_GTE
import uz.john.data.remote.VOTE_COUNT_LTE
import uz.john.data.remote.WITH_COMPANIES
import uz.john.data.remote.WITH_GENRES
import uz.john.data.remote.YEAR
import uz.john.data.remote.api.TvShowsApi
import uz.john.data.remote.model.movie.AccountStateOfMediaData
import uz.john.data.remote.model.movie.GenresResponseData
import uz.john.data.remote.model.movie.post.AddToFavoriteRequest
import uz.john.data.remote.model.movie.post.AddToWatchLaterRequest
import uz.john.data.remote.model.tv_show.TvShowsResponseData
import uz.john.data.remote.model.tv_show.tv_show_details.TvShowDetailsData
import uz.john.util.ApiResponse
import uz.john.util.ResultModel
import uz.john.util.invokeRequest
import java.util.Locale
import javax.inject.Inject

class TvShowsRepository @Inject constructor(
    private val tvShowsApi: TvShowsApi,
    private val dataStoreRepository: DataStoreRepository
) {
    private val region = Locale.getDefault().country
    private val language = Locale.getDefault().language

    suspend fun getTrendingTodayTvShows(page: Int): ResultModel<TvShowsResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            tvShowsApi.getTrendingTodayTvShows(
                language = language,
                page = page
            )
        }
    }

    suspend fun getTrendingThisWeekTvShows(page: Int): ResultModel<TvShowsResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            tvShowsApi.getTrendingThisWeekTvShows(
                language = language,
                page = page,
            )
        }
    }

    suspend fun getTopRatedTvShows(page: Int): ResultModel<TvShowsResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            tvShowsApi.getTopRatedTvShows(
                language = language,
                page = page
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

    suspend fun getFavoriteTvShows(
        page: Int,
        accountId: Int = 1
    ): ResultModel<TvShowsResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            val sessionId = dataStoreRepository.userData.first().sessionId

            tvShowsApi.getFavoriteTvShows(
                accountId = accountId,
                page = page,
                sessionId = sessionId,
                language = language
            )
        }
    }

    suspend fun getWatchLaterTvShows(
        page: Int,
        accountId: Int = 1
    ): ResultModel<TvShowsResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            val sessionId = dataStoreRepository.userData.first().sessionId

            tvShowsApi.getWatchLaterTvShows(
                accountId = accountId,
                page = page,
                sessionId = sessionId,
                language = language
            )
        }
    }

    suspend fun setTvShowFavorite(tvShowId: Int, setFavorite: Boolean): ResultModel<ApiResponse> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            val sessionId = dataStoreRepository.userData.first().sessionId

            tvShowsApi.setTvShowFavorite(
                sessionId = sessionId,
                addToFavoriteRequest = AddToFavoriteRequest(
                    mediaType = TV_SHOW_MEDIA_TYPE,
                    mediaId = tvShowId,
                    favorite = setFavorite
                )
            )
        }
    }

    suspend fun setTvShowWatchLater(tvShowId: Int, setWatchLater: Boolean): ResultModel<ApiResponse> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            val sessionId = dataStoreRepository.userData.first().sessionId

            tvShowsApi.setTvShowWatchLater(
                sessionId = sessionId,
                addToWatchLaterRequest = AddToWatchLaterRequest(
                    mediaType = TV_SHOW_MEDIA_TYPE,
                    mediaId = tvShowId,
                    watchlist = setWatchLater
                )
            )
        }
    }

    suspend fun getAccountStatesOfTvShow(tvShowId: Int): ResultModel<AccountStateOfMediaData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            val sessionId = dataStoreRepository.userData.first().sessionId

            tvShowsApi.getAccountStateOfTvShow(tvShowId = tvShowId, sessionId = sessionId)
        }
    }

    suspend fun searchTvShows(
        query: String,
        page: Int,
        firstAirDateYear: Int? = null,
        year: String? = null
    ): ResultModel<TvShowsResponseData> = invokeRequest {
        val additionalParams = mutableMapOf<String, String>()
        firstAirDateYear?.let { additionalParams[FIRST_AIR_DATE_YEAR] = firstAirDateYear.toString() }
        year?.let { additionalParams[YEAR] = year }
        return@invokeRequest withContext(Dispatchers.IO) {
            val includeAdult = dataStoreRepository.userData.first().includeAdult

            tvShowsApi.searchTvShows(
                query = query,
                includeAdult = includeAdult,
                language = language,
                page = page,
                additionalParams = additionalParams
            )
        }
    }

    suspend fun getAllGenres(): ResultModel<GenresResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            tvShowsApi.getAllGenres(
                language = language,
            )
        }
    }

    suspend fun discoverTvShows(
        page: Int,
        airDataGte: String? = null,
        airDataLte: String? = null,
        firstAirDataYear: String? = null,
        firstAirDataGte: String? = null,
        firstAirDataLte: String? = null,
        screenedTheatrically: Boolean? = null,
        sortBy: String? = null,
        timeZone: String? = null,
        voteAverageGte: Float? = null,
        voteAverageLte: Float? = null,
        voteCountGte: Float? = null,
        voteCountLte: Float? = null,
        withCompanies: String? = null,
        withGenres: String? = null,
    ): ResultModel<TvShowsResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            val queryParams = mutableMapOf<String, String>()
            queryParams[INCLUDE_ADULT] = dataStoreRepository.userData.first().includeAdult.toString()

            airDataGte?.let { queryParams[AIR_DATE_GTE] = airDataGte.toString() }
            airDataLte?.let { queryParams[AIR_DATE_LTE] = airDataLte.toString() }
            firstAirDataYear?.let { queryParams[FIRST_AIR_DATE_YEAR] = firstAirDataYear.toString() }
            firstAirDataGte?.let { queryParams[FIRST_AIR_DATE_GTE] = firstAirDataGte.toString() }
            firstAirDataLte?.let { queryParams[FIRST_AIR_DATE_LTE] = firstAirDataLte.toString() }
            screenedTheatrically?.let { queryParams[SCREENED_THEATRICALLY] = screenedTheatrically.toString() }
            sortBy?.let { queryParams[SORT_BY] = sortBy.toString() }
            timeZone?.let { queryParams[TIMEZONE] = timeZone.toString() }
            voteAverageGte?.let { queryParams[VOTE_AVERAGE_GTE] = voteAverageGte.toString() }
            voteAverageLte?.let { queryParams[VOTE_AVERAGE_LTE] = voteAverageLte.toString() }
            voteCountGte?.let { queryParams[VOTE_COUNT_GTE] = voteCountGte.toString() }
            voteCountLte?.let { queryParams[VOTE_COUNT_LTE] = voteCountLte.toString() }
            withCompanies?.let { queryParams[WITH_COMPANIES] = withCompanies.toString() }
            withGenres?.let { queryParams[WITH_GENRES] = withGenres.toString() }

            tvShowsApi.discoverTvShows(
                page = page,
                language = language,
                queryParams = queryParams,
            )
        }
    }
}