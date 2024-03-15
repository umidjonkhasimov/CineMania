package uz.john.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.john.data.remote.api.PersonApi
import uz.john.data.remote.model.person.PersonData
import uz.john.util.ResultModel
import uz.john.util.invokeRequest
import javax.inject.Inject

class PersonRepository @Inject constructor(
    private val personApi: PersonApi
) {
    suspend fun getPersonDetails(personId: Int): ResultModel<PersonData> = invokeRequest {
        return@invokeRequest withContext(Dispatchers.IO) {
            personApi.getPersonDetails(personId)
        }
    }
}