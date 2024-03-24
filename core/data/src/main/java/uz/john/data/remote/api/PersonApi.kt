package uz.john.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.john.data.remote.APPEND_TO_RESPONSE
import uz.john.data.remote.APPEND_IMAGES
import uz.john.data.remote.APPEND_MOVIE_CREDITS
import uz.john.data.remote.APPEND_TV_SHOW_CREDITS
import uz.john.data.remote.INCLUDE_ADULT
import uz.john.data.remote.LANGUAGE
import uz.john.data.remote.PAGE
import uz.john.data.remote.PERSON_DETAILS_ENDPOINT
import uz.john.data.remote.PERSON_ID
import uz.john.data.remote.POPULAR_PEOPLE_ENDPOINT
import uz.john.data.remote.QUERY
import uz.john.data.remote.SEARCH_PEOPLE_ENDPOINT
import uz.john.data.remote.model.person.PeopleResponseData
import uz.john.data.remote.model.person.details.PersonDetailsData

interface PersonApi {
    @GET(PERSON_DETAILS_ENDPOINT)
    suspend fun getPersonDetails(
        @Path(value = PERSON_ID) personId: Int,
        @Query(value = APPEND_TO_RESPONSE) addToResponse: String = "$APPEND_MOVIE_CREDITS,$APPEND_TV_SHOW_CREDITS,$APPEND_IMAGES"
    ): Response<PersonDetailsData>

    @GET(POPULAR_PEOPLE_ENDPOINT)
    suspend fun getPopularPeople(
        @Query(PAGE) page: Int,
        @Query(LANGUAGE) language: String
    ): Response<PeopleResponseData>

    @GET(SEARCH_PEOPLE_ENDPOINT)
    suspend fun searchPeople(
        @Query(QUERY) query: String,
        @Query(INCLUDE_ADULT) includeAdult: Boolean,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
    ): Response<PeopleResponseData>
}