package uz.john.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.john.data.remote.ADD_TO_RESPONSE
import uz.john.data.remote.APPEND_CREDITS
import uz.john.data.remote.APPEND_IMAGES
import uz.john.data.remote.PERSON_DETAILS_ENDPOINT
import uz.john.data.remote.PERSON_ID
import uz.john.data.remote.model.person.PersonData

interface PersonApi {
    @GET(PERSON_DETAILS_ENDPOINT)
    suspend fun getPersonDetails(
        @Path(value = PERSON_ID) personId: Int,
        @Query(value = ADD_TO_RESPONSE) addToResponse: String = "$APPEND_CREDITS,$APPEND_IMAGES"
    ): Response<PersonData>
}