package uz.john.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.john.data.remote.FIRST_AIR_DATE_YEAR
import uz.john.data.remote.PRIMARY_RELEASE_YEAR
import uz.john.data.remote.YEAR
import uz.john.data.remote.api.SearchApi
import uz.john.data.remote.model.movie.MoviesResponseData
import uz.john.data.remote.model.person.PeopleResponseData
import uz.john.data.remote.model.tv_show.TvShowsResponseData
import uz.john.util.ResultModel
import uz.john.util.invokeRequest
import java.util.Locale
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val searchApi: SearchApi
) {
    private val region = Locale.getDefault().country
    private val language = Locale.getDefault().language

    suspend fun searchMovies(
        query: String,
        page: Int,
        includeAdult: Boolean = true,
        primaryReleaseYear: String? = null,
        year: String? = null
    ): ResultModel<MoviesResponseData> = invokeRequest {
        val additionalQueries = mutableMapOf<String, String>()
        primaryReleaseYear?.let { additionalQueries[PRIMARY_RELEASE_YEAR] = primaryReleaseYear }
        year?.let { additionalQueries[YEAR] = year }

        return@invokeRequest withContext(Dispatchers.IO) {
            searchApi.searchMovies(
                query = query,
                page = page,
                includeAdult = includeAdult,
                language = language,
                region = region,
                additionalParams = additionalQueries,
            )
        }
    }

    suspend fun searchPeople(
        query: String,
        page: Int,
        includeAdult: Boolean = true
    ): ResultModel<PeopleResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            searchApi.searchPeople(
                query = query,
                includeAdult = includeAdult,
                language = language,
                page = page,
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
            searchApi.searchTvShows(
                query = query,
                includeAdult = includeAdult,
                language = language,
                page = page,
                additionalParams = additionalParams
            )
        }
    }
}