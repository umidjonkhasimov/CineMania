package uz.john.domain.model.person

import uz.john.data.remote.model.person.PeopleResponseData

data class PeopleResponse(
    val page: Int,
    val results: List<Person>,
    val totalPages: Int,
    val totalResults: Int
)

fun PeopleResponseData.toDomain(): PeopleResponse {
    return PeopleResponse(
        page = page,
        results = results.map { it.toDomain() },
        totalPages = totalPages,
        totalResults = totalResults
    )
}