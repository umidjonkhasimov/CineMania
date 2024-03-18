package uz.john.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import uz.john.data.remote.INCLUDE_ADULT
import uz.john.data.remote.LANGUAGE
import uz.john.data.remote.PAGE
import uz.john.data.remote.QUERY
import uz.john.data.remote.REGION
import uz.john.data.remote.SEARCH_MOVIE_ENDPOINT
import uz.john.data.remote.SEARCH_PEOPLE_ENDPOINT
import uz.john.data.remote.SEARCH_TV_SHOWS_ENDPOINT
import uz.john.data.remote.model.movie.MoviesResponseData
import uz.john.data.remote.model.person.PeopleResponseData
import uz.john.data.remote.model.tv_show.TvShowsResponseData

interface SearchApi {
    @GET(SEARCH_MOVIE_ENDPOINT)
    suspend fun searchMovies(
        @Query(QUERY) query: String,
        @Query(INCLUDE_ADULT) includeAdult: Boolean,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
        @Query(REGION) region: String,
        @QueryMap additionalParams: Map<String, String>,
    ): Response<MoviesResponseData>

    @GET(SEARCH_PEOPLE_ENDPOINT)
    suspend fun searchPeople(
        @Query(QUERY) query: String,
        @Query(INCLUDE_ADULT) includeAdult: Boolean,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
    ): Response<PeopleResponseData>

    @GET(SEARCH_TV_SHOWS_ENDPOINT)
    suspend fun searchTvShows(
        @Query(QUERY) query: String,
        @Query(INCLUDE_ADULT) includeAdult: Boolean,
        @Query(LANGUAGE) language: String,
        @Query(PAGE) page: Int,
        @QueryMap additionalParams: Map<String, String>,
    ): Response<TvShowsResponseData>
}