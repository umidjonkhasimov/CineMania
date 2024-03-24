package uz.john.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.john.data.remote.api.PersonApi
import uz.john.data.remote.model.person.PeopleResponseData
import uz.john.data.remote.model.person.details.PersonDetailsData
import uz.john.util.ResultModel
import uz.john.util.invokeRequest
import java.util.Locale
import javax.inject.Inject

class PersonRepository @Inject constructor(
    private val personApi: PersonApi
) {
    private val region = Locale.getDefault().country
    private val language = Locale.getDefault().language

    suspend fun getPersonDetails(personId: Int): ResultModel<PersonDetailsData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            personApi.getPersonDetails(personId)
        }
    }

    suspend fun getPopularPeople(page: Int): ResultModel<PeopleResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            personApi.getPopularPeople(
                page = page,
                language = language
            )
        }
    }

    suspend fun searchPeople(
        query: String,
        page: Int,
        includeAdult: Boolean = true
    ): ResultModel<PeopleResponseData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            personApi.searchPeople(
                query = query,
                includeAdult = includeAdult,
                language = language,
                page = page,
            )
        }
    }
}